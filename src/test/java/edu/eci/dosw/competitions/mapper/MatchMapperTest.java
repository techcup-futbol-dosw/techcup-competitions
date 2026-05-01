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

    @BeforeEach
    void setUp() {
        matchMapper = Mappers.getMapper(MatchMapper.class);
    }

    // ===== UNIT TESTS =====

    @Test
    void testToResponseDTO_mapsAllFields() {
        Match match = new Match();
        match.setId("match-1");
        match.setTournamentId("tournament-1");
        match.setHomeTeamId("home-1");
        match.setAwayTeamId("away-1");
        match.setRefereeId("referee-1");
        match.setFieldId("field-1");
        match.setScheduledAt(LocalDateTime.of(2026, 5, 1, 15, 0));
        match.setStatus(MatchStatus.SCHEDULED);
        match.setPhase(MatchPhase.GROUP_STAGE);
        match.setHomeScore(2);
        match.setAwayScore(1);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertEquals("match-1", dto.getId());
        assertEquals("tournament-1", dto.getTournamentId());
        assertEquals("home-1", dto.getHomeTeamId());
        assertEquals("away-1", dto.getAwayTeamId());
        assertEquals("referee-1", dto.getRefereeId());
        assertEquals("field-1", dto.getFieldId());
        assertEquals(LocalDateTime.of(2026, 5, 1, 15, 0), dto.getScheduledAt());
        assertEquals("SCHEDULED", dto.getStatus());
        assertEquals("GROUP_STAGE", dto.getPhase());
        assertEquals(2, dto.getHomeScore());
        assertEquals(1, dto.getAwayScore());
    }

    @Test
    void testToResponseDTO_statusInProgress() {
        Match match = new Match();
        match.setId("match-2");
        match.setTournamentId("t-2");
        match.setHomeTeamId("h-2");
        match.setAwayTeamId("a-2");
        match.setScheduledAt(LocalDateTime.now());
        match.setStatus(MatchStatus.IN_PROGRESS);
        match.setPhase(MatchPhase.QUARTERFINALS);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertEquals("IN_PROGRESS", dto.getStatus());
        assertEquals("QUARTERFINALS", dto.getPhase());
    }

    @Test
    void testToResponseDTO_statusFinished() {
        Match match = new Match();
        match.setId("match-3");
        match.setTournamentId("t-3");
        match.setHomeTeamId("h-3");
        match.setAwayTeamId("a-3");
        match.setScheduledAt(LocalDateTime.now());
        match.setStatus(MatchStatus.FINISHED);
        match.setPhase(MatchPhase.SEMIFINALS);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertEquals("FINISHED", dto.getStatus());
        assertEquals("SEMIFINALS", dto.getPhase());
    }

    @Test
    void testToResponseDTO_statusCancelled() {
        Match match = new Match();
        match.setId("match-4");
        match.setTournamentId("t-4");
        match.setHomeTeamId("h-4");
        match.setAwayTeamId("a-4");
        match.setScheduledAt(LocalDateTime.now());
        match.setStatus(MatchStatus.CANCELLED);
        match.setPhase(MatchPhase.FINAL);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertEquals("CANCELLED", dto.getStatus());
        assertEquals("FINAL", dto.getPhase());
    }

    @Test
    void testToResponseDTO_nullOptionalFields() {
        Match match = new Match();
        match.setId("match-5");
        match.setTournamentId("t-5");
        match.setHomeTeamId("h-5");
        match.setAwayTeamId("a-5");
        match.setScheduledAt(LocalDateTime.now());
        match.setStatus(MatchStatus.SCHEDULED);
        match.setPhase(MatchPhase.GROUP_STAGE);

        MatchResponseDTO dto = matchMapper.toResponseDTO(match);

        assertNull(dto.getRefereeId());
        assertNull(dto.getFieldId());
        assertEquals(0, dto.getHomeScore());
        assertEquals(0, dto.getAwayScore());
    }

    @Test
    void testToEventResponseDTO_mapsGoalEvent() {
        Goal goal = new Goal();
        goal.setId("event-1");
        goal.setMatchId("match-1");
        goal.setTeamId("team-1");
        goal.setPlayerId("player-1");
        goal.setMinute(45);
        goal.setOwnGoal(false);
        goal.setAssistPlayerId("player-2");

        MatchEventResponseDTO dto = matchMapper.toEventResponseDTO(goal);

        assertEquals("event-1", dto.getId());
        assertEquals("match-1", dto.getMatchId());
        assertEquals("team-1", dto.getTeamId());
        assertEquals("player-1", dto.getPlayerId());
        assertEquals(45, dto.getMinute());
        assertNotNull(dto.getDescription());
    }

    @Test
    void testToEventResponseDTO_mapsCardEvent() {
        Card card = new Card();
        card.setId("event-2");
        card.setMatchId("match-1");
        card.setTeamId("team-1");
        card.setPlayerId("player-3");
        card.setMinute(60);
        card.setCardType(CardType.RED);

        MatchEventResponseDTO dto = matchMapper.toEventResponseDTO(card);

        assertEquals("event-2", dto.getId());
        assertEquals("match-1", dto.getMatchId());
        assertEquals("team-1", dto.getTeamId());
        assertEquals("player-3", dto.getPlayerId());
        assertEquals(60, dto.getMinute());
        assertNotNull(dto.getDescription());
    }

    @Test
    void testToStandingsResponseDTO_mapsAllFields() {
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

        StandingsResponseDTO dto = matchMapper.toStandingsResponseDTO(standings);

        assertEquals("standings-1", dto.getId());
        assertEquals("tournament-1", dto.getTournamentId());
        assertEquals("team-1", dto.getTeamId());
        assertEquals(10, dto.getMatchesPlayed());
        assertEquals(6, dto.getMatchesWon());
        assertEquals(2, dto.getMatchesDrawn());
        assertEquals(2, dto.getMatchesLost());
        assertEquals(20, dto.getGoalsFor());
        assertEquals(10, dto.getGoalsAgainst());
        assertEquals(10, dto.getGoalDifference());
        assertEquals(20, dto.getPoints());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testToResponseDTO_matchWithScoresAndAllPhases() {
        Match match = new Match();
        match.setId("int-1");
        match.setTournamentId("t-int");
        match.setHomeTeamId("home-int");
        match.setAwayTeamId("away-int");
        match.setRefereeId("ref-int");
        match.setFieldId("field-int");
        match.setScheduledAt(LocalDateTime.of(2026, 6, 15, 20, 0));
        match.setStatus(MatchStatus.FINISHED);
        match.setPhase(MatchPhase.FINAL);
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
    void testToStandingsResponseDTO_zeroValues() {
        Standings standings = new Standings();
        standings.setId("standings-2");
        standings.setTournamentId("tournament-2");
        standings.setTeamId("team-2");

        StandingsResponseDTO dto = matchMapper.toStandingsResponseDTO(standings);

        assertEquals("standings-2", dto.getId());
        assertEquals(0, dto.getMatchesPlayed());
        assertEquals(0, dto.getPoints());
        assertEquals(0, dto.getGoalDifference());
    }

    @Test
    void testToEventResponseDTO_goalWithOwnGoal() {
        Goal goal = new Goal();
        goal.setId("event-own");
        goal.setMatchId("match-1");
        goal.setTeamId("team-1");
        goal.setPlayerId("player-1");
        goal.setMinute(88);
        goal.setOwnGoal(true);

        MatchEventResponseDTO dto = matchMapper.toEventResponseDTO(goal);

        assertEquals("event-own", dto.getId());
        assertEquals(88, dto.getMinute());
        assertTrue(dto.getDescription().contains("Own goal"));
    }

    @Test
    void testToEventResponseDTO_goalWithAssist() {
        Goal goal = new Goal();
        goal.setId("event-assist");
        goal.setMatchId("match-1");
        goal.setTeamId("team-1");
        goal.setPlayerId("player-1");
        goal.setAssistPlayerId("player-2");
        goal.setMinute(25);
        goal.setOwnGoal(false);

        MatchEventResponseDTO dto = matchMapper.toEventResponseDTO(goal);

        assertEquals("event-assist", dto.getId());
        assertEquals(25, dto.getMinute());
        assertTrue(dto.getDescription().contains("assisted by"));
    }

    @Test
    void testToEventResponseDTO_yellowCard() {
        Card card = new Card();
        card.setId("card-yellow");
        card.setMatchId("match-1");
        card.setTeamId("team-1");
        card.setPlayerId("player-4");
        card.setMinute(35);
        card.setCardType(CardType.YELLOW);

        MatchEventResponseDTO dto = matchMapper.toEventResponseDTO(card);

        assertEquals("card-yellow", dto.getId());
        assertTrue(dto.getDescription().contains("YELLOW"));
    }
}
