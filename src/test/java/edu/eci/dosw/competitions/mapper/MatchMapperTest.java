package edu.eci.dosw.competitions.mapper;

import edu.eci.dosw.competitions.dtos.MatchEventResponseDTO;
import edu.eci.dosw.competitions.dtos.MatchResponseDTO;
import edu.eci.dosw.competitions.dtos.StandingsResponseDTO;
import edu.eci.dosw.competitions.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchMapperTest {

    private MatchMapper matchMapper;
    private MatchEventMapper matchEventMapper;
    private StandingsMapper standingsMapper;
    private Match baseMatch;

    @BeforeEach
    void setUp() {
        matchMapper = Mappers.getMapper(MatchMapper.class);
        matchEventMapper = Mappers.getMapper(MatchEventMapper.class);
        standingsMapper = Mappers.getMapper(StandingsMapper.class);
        baseMatch = buildMatch("match-1", MatchStatus.SCHEDULED, MatchPhase.GROUP_STAGE);
    }

    private Match buildMatch(String id, MatchStatus status, MatchPhase phase) {
        Match match = new Match();
        match.setId(id);
        match.setTournamentId("tournament-" + id);
        match.setHomeTeamId("home-" + id);
        match.setAwayTeamId("away-" + id);
        match.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        match.setStatus(status);
        match.setPhase(phase);
        return match;
    }

    private Goal buildGoal(String id, String playerId, int minute, boolean ownGoal, String assistId) {
        Goal goal = new Goal();
        goal.setId(id);
        goal.setMatchId("match-1");
        goal.setTeamId("team-1");
        goal.setPlayerId(playerId);
        goal.setMinute(minute);
        goal.setOwnGoal(ownGoal);
        goal.setAssistPlayerId(assistId);
        return goal;
    }

    @Test
    void testToResponseDTO_mapsAllFields() {
        baseMatch.setRefereeId("referee-1");
        baseMatch.setFieldId("field-1");
        baseMatch.setHomeScore(2);
        baseMatch.setAwayScore(1);

        MatchResponseDTO dto = matchMapper.toResponseDTO(baseMatch);

        assertEquals("match-1", dto.getId());
        assertEquals("tournament-match-1", dto.getTournamentId());
        assertEquals("home-match-1", dto.getHomeTeamId());
        assertEquals("away-match-1", dto.getAwayTeamId());
        assertEquals("referee-1", dto.getRefereeId());
        assertEquals("field-1", dto.getFieldId());
        assertEquals("SCHEDULED", dto.getStatus());
        assertEquals("GROUP_STAGE", dto.getPhase());
        assertEquals(2, dto.getHomeScore());
        assertEquals(1, dto.getAwayScore());
    }

    @Test
    void testToResponseDTO_statusInProgress() {
        Match match = buildMatch("m-2", MatchStatus.IN_PROGRESS, MatchPhase.QUARTERFINALS);
        MatchResponseDTO dto = matchMapper.toResponseDTO(match);
        assertEquals("IN_PROGRESS", dto.getStatus());
        assertEquals("QUARTERFINALS", dto.getPhase());
    }

    @Test
    void testToResponseDTO_statusFinished() {
        Match match = buildMatch("m-3", MatchStatus.FINISHED, MatchPhase.SEMIFINALS);
        MatchResponseDTO dto = matchMapper.toResponseDTO(match);
        assertEquals("FINISHED", dto.getStatus());
        assertEquals("SEMIFINALS", dto.getPhase());
    }

    @Test
    void testToResponseDTO_statusCancelled() {
        Match match = buildMatch("m-4", MatchStatus.CANCELLED, MatchPhase.FINAL);
        MatchResponseDTO dto = matchMapper.toResponseDTO(match);
        assertEquals("CANCELLED", dto.getStatus());
        assertEquals("FINAL", dto.getPhase());
    }

    @Test
    void testToResponseDTO_nullOptionalFields() {
        MatchResponseDTO dto = matchMapper.toResponseDTO(baseMatch);
        assertNull(dto.getRefereeId());
        assertNull(dto.getFieldId());
        assertEquals(0, dto.getHomeScore());
        assertEquals(0, dto.getAwayScore());
    }

    @Test
    void testToResponseDTO_matchWithScoresAndAllPhases() {
        Match match = buildMatch("int-1", MatchStatus.FINISHED, MatchPhase.FINAL);
        match.setHomeScore(3);
        match.setAwayScore(2);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertEquals("int-1", dto.getId());
        assertEquals("FINISHED", dto.getStatus());
        assertEquals("FINAL", dto.getPhase());
        assertEquals(3, dto.getHomeScore());
        assertEquals(2, dto.getAwayScore());
    }

    @Test
    void testEventMapper_mapsGoalEvent() {
        Goal goal = buildGoal("event-1", "player-1", 45, false, "player-2");
        MatchEventResponseDTO dto = matchEventMapper.toResponseDTO(goal);

        assertEquals("event-1", dto.getId());
        assertEquals("match-1", dto.getMatchId());
        assertEquals("team-1", dto.getTeamId());
        assertEquals("player-1", dto.getPlayerId());
        assertEquals(45, dto.getMinute());
        assertNotNull(dto.getDescription());
    }

    @Test
    void testEventMapper_mapsCardEvent() {
        Card card = new Card();
        card.setId("event-2");
        card.setMatchId("match-1");
        card.setTeamId("team-1");
        card.setPlayerId("player-3");
        card.setMinute(60);
        card.setCardType(CardType.RED);

        MatchEventResponseDTO dto = matchEventMapper.toResponseDTO(card);

        assertEquals("event-2", dto.getId());
        assertEquals("player-3", dto.getPlayerId());
        assertEquals(60, dto.getMinute());
        assertNotNull(dto.getDescription());
    }

    @Test
    void testEventMapper_goalWithOwnGoal() {
        Goal goal = buildGoal("event-own", "player-1", 88, true, null);
        MatchEventResponseDTO dto = matchEventMapper.toResponseDTO(goal);
        assertEquals("event-own", dto.getId());
        assertTrue(dto.getDescription().contains("Own goal"));
    }

    @Test
    void testEventMapper_goalWithAssist() {
        Goal goal = buildGoal("event-assist", "player-1", 25, false, "player-2");
        MatchEventResponseDTO dto = matchEventMapper.toResponseDTO(goal);
        assertEquals("event-assist", dto.getId());
        assertTrue(dto.getDescription().contains("assisted by"));
    }

    @Test
    void testEventMapper_yellowCard() {
        Card card = new Card();
        card.setId("card-yellow");
        card.setMatchId("match-1");
        card.setTeamId("team-1");
        card.setPlayerId("player-4");
        card.setMinute(35);
        card.setCardType(CardType.YELLOW);

        MatchEventResponseDTO dto = matchEventMapper.toResponseDTO(card);
        assertEquals("card-yellow", dto.getId());
        assertTrue(dto.getDescription().contains("YELLOW"));
    }

    @Test
    void testStandingsMapper_mapsAllFields() {
        Standings standings = new Standings();
        standings.setId("standings-1");
        standings.setTournamentId("tournament-1");
        standings.setTeamId("team-1");
        standings.setMatchesPlayed(10);
        standings.setMatchesWon(6);
        standings.setMatchesDrawn(2);
        standings.setMatchesLost(2);
        standings.setGoalsFor(20);
        standings.setGoalsAgainst(10);
        standings.setGoalDifference(10);
        standings.setPoints(20);

        StandingsResponseDTO dto = standingsMapper.toResponseDTO(standings);

        assertEquals("standings-1", dto.getId());
        assertEquals(10, dto.getMatchesPlayed());
        assertEquals(6, dto.getMatchesWon());
        assertEquals(2, dto.getMatchesDrawn());
        assertEquals(2, dto.getMatchesLost());
        assertEquals(20, dto.getGoalsFor());
        assertEquals(10, dto.getGoalsAgainst());
        assertEquals(20, dto.getPoints());
    }

    @Test
    void testStandingsMapper_zeroValues() {
        Standings standings = new Standings();
        standings.setId("standings-2");
        standings.setTournamentId("tournament-2");
        standings.setTeamId("team-2");

        StandingsResponseDTO dto = standingsMapper.toResponseDTO(standings);

        assertEquals("standings-2", dto.getId());
        assertEquals(0, dto.getMatchesPlayed());
        assertEquals(0, dto.getPoints());
        assertEquals(0, dto.getGoalDifference());
    }
}
