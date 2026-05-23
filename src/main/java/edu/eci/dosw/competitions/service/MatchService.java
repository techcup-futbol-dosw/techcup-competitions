package edu.eci.dosw.competitions.service;

import edu.eci.dosw.competitions.dtos.CreateMatchDTO;
import edu.eci.dosw.competitions.dtos.RegisterCardDTO;
import edu.eci.dosw.competitions.dtos.RegisterGoalDTO;
import edu.eci.dosw.competitions.dtos.UpdateMatchDTO;
import edu.eci.dosw.competitions.entity.*;
import edu.eci.dosw.competitions.model.MatchModel;
import edu.eci.dosw.competitions.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchEventRepository matchEventRepository;
    private final MatchAuditRepository matchAuditRepository;
    private final StandingsRepository standingsRepository;

    public MatchService(MatchRepository matchRepository,
                        MatchEventRepository matchEventRepository,
                        MatchAuditRepository matchAuditRepository,
                        StandingsRepository standingsRepository) {
        this.matchRepository = matchRepository;
        this.matchEventRepository = matchEventRepository;
        this.matchAuditRepository = matchAuditRepository;
        this.standingsRepository = standingsRepository;
    }

    public Match createMatch(CreateMatchDTO dto) {
        Match match = new MatchBuilder()
                .withTournament(dto.getTournamentId())
                .withTeams(dto.getHomeTeamId(), dto.getAwayTeamId())
                .withReferee(dto.getRefereeId())
                .withField(dto.getFieldId())
                .withDate(dto.getScheduledAt())
                .withPhase(MatchPhase.valueOf(dto.getPhase()))
                .build();

        match.setId(UUID.randomUUID().toString());
        matchRepository.save(match);
        logAudit(match.getId(), MatchAuditAction.CREATED, "Match created");
        return match;
    }

    public Match updateMatch(String id, UpdateMatchDTO dto) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found: " + id));

        MatchModel model = new MatchModel(match);
        model.modify();

        if (dto.getRefereeId() != null) match.setRefereeId(dto.getRefereeId());
        if (dto.getFieldId() != null) match.setFieldId(dto.getFieldId());
        if (dto.getScheduledAt() != null) match.setScheduledAt(dto.getScheduledAt());

        matchRepository.save(match);
        logAudit(id, MatchAuditAction.UPDATED, "Match updated");
        return match;
    }

    public void deleteMatch(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found: " + id));

        MatchModel model = new MatchModel(match);
        model.delete();

        logAudit(id, MatchAuditAction.DELETED, "Match deleted");
        matchRepository.deleteById(id);
    }

    public Match startMatch(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found: " + id));

        MatchModel model = new MatchModel(match);
        model.start();

        matchRepository.save(match);
        logAudit(id, MatchAuditAction.UPDATED, "Match started");
        return match;
    }

    public Match finishMatch(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found: " + id));

        MatchModel model = new MatchModel(match);
        model.finish();

        matchRepository.save(match);
        updateStandings(match);
        logAudit(id, MatchAuditAction.UPDATED, "Match finished");
        return match;
    }

    public Goal registerGoal(RegisterGoalDTO dto) {
        Match match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new RuntimeException("Match not found: " + dto.getMatchId()));

        MatchModel model = new MatchModel(match);
        model.registerGoal();

        Goal goal = new Goal();
        goal.setId(UUID.randomUUID().toString());
        goal.setMatchId(dto.getMatchId());
        goal.setTeamId(dto.getTeamId());
        goal.setPlayerId(dto.getPlayerId());
        goal.setAssistPlayerId(dto.getAssistPlayerId());
        goal.setOwnGoal(dto.isOwnGoal());
        goal.setMinute(dto.getMinute());

        matchEventRepository.save(goal);

        if (dto.isOwnGoal()) {
            if (dto.getTeamId().equals(match.getHomeTeamId())) {
                match.setAwayScore(match.getAwayScore() + 1);
            } else {
                match.setHomeScore(match.getHomeScore() + 1);
            }
        } else {
            if (dto.getTeamId().equals(match.getHomeTeamId())) {
                match.setHomeScore(match.getHomeScore() + 1);
            } else {
                match.setAwayScore(match.getAwayScore() + 1);
            }
        }

        matchRepository.save(match);
        logAudit(dto.getMatchId(), MatchAuditAction.UPDATED, "Goal registered - minute " + dto.getMinute());
        return goal;
    }

    public Card registerCard(RegisterCardDTO dto) {
        Match match = matchRepository.findById(dto.getMatchId())
                .orElseThrow(() -> new RuntimeException("Match not found: " + dto.getMatchId()));

        MatchModel model = new MatchModel(match);
        model.registerCard();

        Card card = new Card();
        card.setId(UUID.randomUUID().toString());
        card.setMatchId(dto.getMatchId());
        card.setTeamId(dto.getTeamId());
        card.setPlayerId(dto.getPlayerId());
        card.setCardType(CardType.valueOf(dto.getCardType()));
        card.setMinute(dto.getMinute());

        matchEventRepository.save(card);
        logAudit(dto.getMatchId(), MatchAuditAction.UPDATED, "Card registered - " + dto.getCardType() + " minute " + dto.getMinute());
        return card;
    }

    public List<MatchEvent> getEventsByMatch(String matchId) {
        return matchEventRepository.findByMatchId(matchId);
    }

    // ── Privados ────────────────────────────────────────────────────────────

    private void logAudit(String matchId, MatchAuditAction action, String detail) {
        MatchAudit audit = new MatchAudit();
        audit.setId(UUID.randomUUID().toString());
        audit.setMatchId(matchId);
        audit.setAction(action);
        audit.setDetail(detail);
        audit.setTimestamp(LocalDateTime.now());
        matchAuditRepository.save(audit);
    }

    private void updateStandings(Match match) {
        Standings home = standingsRepository
                .findByTournamentIdAndTeamId(match.getTournamentId(), match.getHomeTeamId())
                .orElseGet(() -> createStandings(match.getTournamentId(), match.getHomeTeamId()));

        Standings away = standingsRepository
                .findByTournamentIdAndTeamId(match.getTournamentId(), match.getAwayTeamId())
                .orElseGet(() -> createStandings(match.getTournamentId(), match.getAwayTeamId()));

        home.updateAfterMatch(match.getHomeScore(), match.getAwayScore());
        away.updateAfterMatch(match.getAwayScore(), match.getHomeScore());

        standingsRepository.save(home);
        standingsRepository.save(away);
    }

    private Standings createStandings(String tournamentId, String teamId) {
        Standings s = new Standings();
        s.setId(UUID.randomUUID().toString());
        s.setTournamentId(tournamentId);
        s.setTeamId(teamId);
        return s;
    }
    public List<Match> getMatchesByTournament(String tournamentId) {
        return matchRepository.findByTournamentId(tournamentId);
    }
    public Match resetMatch(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found: " + id));
        match.setStatus(MatchStatus.SCHEDULED);
        match.setHomeScore(0);
        match.setAwayScore(0);
        matchRepository.save(match);
        logAudit(id, MatchAuditAction.UPDATED, "Match reset to SCHEDULED");
        return match;
    }
}