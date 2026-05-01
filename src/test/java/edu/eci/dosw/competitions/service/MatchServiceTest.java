package edu.eci.dosw.competitions.service;

import edu.eci.dosw.competitions.dtos.*;
import edu.eci.dosw.competitions.entity.*;
import edu.eci.dosw.competitions.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock private MatchRepository matchRepository;
    @Mock private MatchEventRepository matchEventRepository;
    @Mock private MatchAuditRepository matchAuditRepository;
    @Mock private StandingsRepository standingsRepository;

    @InjectMocks
    private MatchService matchService;

    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId("match-1");
        match.setTournamentId("tournament-1");
        match.setHomeTeamId("home-1");
        match.setAwayTeamId("away-1");
        match.setScheduledAt(LocalDateTime.now());
        match.setStatus(MatchStatus.SCHEDULED);
        match.setPhase(MatchPhase.GROUP_STAGE);
        match.setHomeScore(0);
        match.setAwayScore(0);
    }

    // ===== UNIT TESTS =====

    @Test
    void testCreateMatch_savesAndLogsAudit() {
        CreateMatchDTO dto = new CreateMatchDTO();
        dto.setTournamentId("tournament-1");
        dto.setHomeTeamId("home-1");
        dto.setAwayTeamId("away-1");
        dto.setScheduledAt(LocalDateTime.now());
        dto.setPhase("GROUP_STAGE");

        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Match result = matchService.createMatch(dto);

        verify(matchRepository, times(1)).save(any(Match.class));
        verify(matchAuditRepository, times(1)).save(any(MatchAudit.class));
        assertEquals(MatchStatus.SCHEDULED, result.getStatus());
    }

    @Test
    void testUpdateMatch_throwsWhenCannotBeModified() {
        match.setStatus(MatchStatus.FINISHED);
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));

        UpdateMatchDTO dto = new UpdateMatchDTO();
        dto.setRefereeId("ref-99");

        assertThrows(RuntimeException.class, () -> matchService.updateMatch("match-1", dto));
    }

    @Test
    void testStartMatch_changesStatusToInProgress() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Match result = matchService.startMatch("match-1");

        assertEquals(MatchStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void testRegisterGoal_ownGoalAddsToOpponent() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(matchEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("home-1");
        dto.setPlayerId("player-1");
        dto.setOwnGoal(true);
        dto.setMinute(30);

        matchService.registerGoal(dto);

        assertEquals(1, match.getAwayScore());
        assertEquals(0, match.getHomeScore());
    }

    @Test
    void testRegisterCard_savesCardAndLogsAudit() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RegisterCardDTO dto = new RegisterCardDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("home-1");
        dto.setPlayerId("player-1");
        dto.setCardType("YELLOW");
        dto.setMinute(45);

        Card result = matchService.registerCard(dto);

        verify(matchEventRepository, times(1)).save(any(Card.class));
        verify(matchAuditRepository, times(1)).save(any(MatchAudit.class));
        assertEquals(CardType.YELLOW, result.getCardType());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testCreateMatch_persistsWithScheduledStatus() {
        CreateMatchDTO dto = new CreateMatchDTO();
        dto.setTournamentId("t-int");
        dto.setHomeTeamId("h-int");
        dto.setAwayTeamId("a-int");
        dto.setScheduledAt(LocalDateTime.now());
        dto.setPhase("GROUP_STAGE");

        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Match result = matchService.createMatch(dto);

        assertEquals(MatchStatus.SCHEDULED, result.getStatus());
        assertEquals("t-int", result.getTournamentId());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testFullMatchLifecycle_createStartRegisterGoalFinish() {
        // crear
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(matchEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(standingsRepository.findByTournamentIdAndTeamId(any(), any()))
                .thenReturn(Optional.empty());
        when(standingsRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // iniciar
        matchService.startMatch("match-1");
        assertEquals(MatchStatus.IN_PROGRESS, match.getStatus());

        // registrar gol
        RegisterGoalDTO goal = new RegisterGoalDTO();
        goal.setMatchId("match-1");
        goal.setTeamId("home-1");
        goal.setPlayerId("player-1");
        goal.setOwnGoal(false);
        goal.setMinute(10);
        matchService.registerGoal(goal);
        assertEquals(1, match.getHomeScore());

        // finalizar
        matchService.finishMatch("match-1");
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testRegisterGoal_nonExistentMatch_throwsException() {
        when(matchRepository.findById("no-existe")).thenReturn(Optional.empty());

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("no-existe");
        dto.setTeamId("home-1");
        dto.setPlayerId("player-1");
        dto.setMinute(5);

        assertThrows(RuntimeException.class, () -> matchService.registerGoal(dto));
    }

    @Test
    void testDeleteMatch_deletesWhenScheduled() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));

        matchService.deleteMatch("match-1");

        verify(matchRepository, times(1)).deleteById("match-1");
        verify(matchAuditRepository, times(1)).save(any(MatchAudit.class));
    }

    @Test
    void testUpdateMatch_updatesFieldsWhenModifiable() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UpdateMatchDTO dto = new UpdateMatchDTO();
        dto.setRefereeId("ref-new");
        dto.setFieldId("field-new");
        dto.setScheduledAt(LocalDateTime.now().plusDays(1));

        Match result = matchService.updateMatch("match-1", dto);

        assertEquals("ref-new", result.getRefereeId());
        assertEquals("field-new", result.getFieldId());
        verify(matchRepository, times(1)).save(any(Match.class));
    }

    @Test
    void testRegisterGoal_normalGoalAwayTeam_addsToAwayScore() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(matchEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("away-1"); // equipo visitante mete gol normal
        dto.setPlayerId("player-2");
        dto.setOwnGoal(false);
        dto.setMinute(60);

        matchService.registerGoal(dto);

        assertEquals(1, match.getAwayScore());
        assertEquals(0, match.getHomeScore());
    }

    @Test
    void testRegisterGoal_ownGoalByAwayTeam_addsToHomeScore() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(matchEventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        RegisterGoalDTO dto = new RegisterGoalDTO();
        dto.setMatchId("match-1");
        dto.setTeamId("away-1"); // visitante mete gol en contra
        dto.setPlayerId("player-2");
        dto.setOwnGoal(true);
        dto.setMinute(70);

        matchService.registerGoal(dto);

        assertEquals(1, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void testFinishMatch_createsStandingsWhenNotExist() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(standingsRepository.findByTournamentIdAndTeamId(any(), any()))
                .thenReturn(Optional.empty()); // standings no existen aún
        when(standingsRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        match.setHomeScore(2);
        match.setAwayScore(1);

        Match result = matchService.finishMatch("match-1");

        assertEquals(MatchStatus.FINISHED, result.getStatus());
        verify(standingsRepository, times(2)).save(any()); // home y away
    }

    @Test
    void testDeleteMatch_throwsWhenNotScheduled() {
        match.setStatus(MatchStatus.FINISHED);
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(match));

        assertThrows(RuntimeException.class, () -> matchService.deleteMatch("match-1"));
        verify(matchRepository, never()).deleteById(any());
    }
}