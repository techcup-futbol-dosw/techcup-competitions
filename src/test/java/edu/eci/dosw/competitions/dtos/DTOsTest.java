package edu.eci.dosw.competitions.dtos;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class DTOsTest {

    // ───────── CreateMatchDTO ─────────
    @Test
    void testCreateMatchDTO() {
        CreateMatchDTO dto = new CreateMatchDTO();
        String tournamentId = UUID.randomUUID().toString();
        String homeTeamId = UUID.randomUUID().toString();
        String awayTeamId = UUID.randomUUID().toString();
        String refereeId = UUID.randomUUID().toString();
        String fieldId = UUID.randomUUID().toString();
        LocalDateTime scheduledAt = LocalDateTime.now();

        dto.setTournamentId(tournamentId);
        dto.setHomeTeamId(homeTeamId);
        dto.setAwayTeamId(awayTeamId);
        dto.setRefereeId(refereeId);
        dto.setFieldId(fieldId);
        dto.setScheduledAt(scheduledAt);
        dto.setPhase("GROUP_STAGE");

        assertEquals(tournamentId, dto.getTournamentId());
        assertEquals(homeTeamId, dto.getHomeTeamId());
        assertEquals(awayTeamId, dto.getAwayTeamId());
        assertEquals(refereeId, dto.getRefereeId());
        assertEquals(fieldId, dto.getFieldId());
        assertEquals(scheduledAt, dto.getScheduledAt());
        assertEquals("GROUP_STAGE", dto.getPhase());
    }

    // ───────── UpdateMatchDTO ─────────
    @Test
    void testUpdateMatchDTO() {
        UpdateMatchDTO dto = new UpdateMatchDTO();
        String refereeId = UUID.randomUUID().toString();
        String fieldId = UUID.randomUUID().toString();
        LocalDateTime scheduledAt = LocalDateTime.now();

        dto.setRefereeId(refereeId);
        dto.setFieldId(fieldId);
        dto.setScheduledAt(scheduledAt);

        assertEquals(refereeId, dto.getRefereeId());
        assertEquals(fieldId, dto.getFieldId());
        assertEquals(scheduledAt, dto.getScheduledAt());
    }

    // ───────── RegisterGoalDTO ─────────
    @Test
    void testRegisterGoalDTO() {
        RegisterGoalDTO dto = new RegisterGoalDTO();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();
        String assistPlayerId = UUID.randomUUID().toString();

        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setPlayerId(playerId);
        dto.setAssistPlayerId(assistPlayerId);
        dto.setOwnGoal(true);
        dto.setMinute(45);

        assertEquals(matchId, dto.getMatchId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals(playerId, dto.getPlayerId());
        assertEquals(assistPlayerId, dto.getAssistPlayerId());
        assertTrue(dto.isOwnGoal());
        assertEquals(45, dto.getMinute());
    }

    // ───────── RegisterCardDTO ─────────
    @Test
    void testRegisterCardDTO() {
        RegisterCardDTO dto = new RegisterCardDTO();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();

        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setPlayerId(playerId);
        dto.setCardType("YELLOW");
        dto.setMinute(30);

        assertEquals(matchId, dto.getMatchId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals(playerId, dto.getPlayerId());
        assertEquals("YELLOW", dto.getCardType());
        assertEquals(30, dto.getMinute());
    }

    // ───────── CreateLineupDTO ─────────
    @Test
    void testCreateLineupDTO() {
        CreateLineupDTO dto = new CreateLineupDTO();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        List<String> starters = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<String> substitutes = List.of(UUID.randomUUID().toString());

        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setFormation("1-4-3-3");
        dto.setStarterIds(starters);
        dto.setSubstituteIds(substitutes);

        assertEquals(matchId, dto.getMatchId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals("1-4-3-3", dto.getFormation());
        assertEquals(starters, dto.getStarterIds());
        assertEquals(substitutes, dto.getSubstituteIds());
    }

    // ───────── UpdateLineupDTO ─────────
    @Test
    void testUpdateLineupDTO() {
        UpdateLineupDTO dto = new UpdateLineupDTO();
        List<String> starters = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<String> substitutes = List.of(UUID.randomUUID().toString());

        dto.setFormation("1-4-4-2");
        dto.setStarterIds(starters);
        dto.setSubstituteIds(substitutes);

        assertEquals("1-4-4-2", dto.getFormation());
        assertEquals(starters, dto.getStarterIds());
        assertEquals(substitutes, dto.getSubstituteIds());
    }

    // ───────── MatchResponseDTO ─────────
    @Test
    void testMatchResponseDTO() {
        MatchResponseDTO dto = new MatchResponseDTO();
        String id = UUID.randomUUID().toString();
        String tournamentId = UUID.randomUUID().toString();
        String homeTeamId = UUID.randomUUID().toString();
        String awayTeamId = UUID.randomUUID().toString();
        LocalDateTime scheduledAt = LocalDateTime.now();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        dto.setHomeTeamId(homeTeamId);
        dto.setAwayTeamId(awayTeamId);
        dto.setScheduledAt(scheduledAt);
        dto.setStatus("SCHEDULED");
        dto.setPhase("FINAL");
        dto.setHomeScore(2);
        dto.setAwayScore(1);

        assertEquals(id, dto.getId());
        assertEquals(tournamentId, dto.getTournamentId());
        assertEquals(homeTeamId, dto.getHomeTeamId());
        assertEquals(awayTeamId, dto.getAwayTeamId());
        assertEquals(scheduledAt, dto.getScheduledAt());
        assertEquals("SCHEDULED", dto.getStatus());
        assertEquals("FINAL", dto.getPhase());
        assertEquals(2, dto.getHomeScore());
        assertEquals(1, dto.getAwayScore());
    }

    // ───────── MatchEventResponseDTO ─────────
    @Test
    void testMatchEventResponseDTO() {
        MatchEventResponseDTO dto = new MatchEventResponseDTO();
        String id = UUID.randomUUID().toString();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        String playerId = UUID.randomUUID().toString();

        dto.setId(id);
        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setPlayerId(playerId);
        dto.setMinute(60);
        dto.setDescription("Gol de cabeza");

        assertEquals(id, dto.getId());
        assertEquals(matchId, dto.getMatchId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals(playerId, dto.getPlayerId());
        assertEquals(60, dto.getMinute());
        assertEquals("Gol de cabeza", dto.getDescription());
    }

    // ───────── LineupResponseDTO ─────────
    @Test
    void testLineupResponseDTO() {
        LineupResponseDTO dto = new LineupResponseDTO();
        String id = UUID.randomUUID().toString();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        List<String> starters = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<String> substitutes = List.of(UUID.randomUUID().toString());

        dto.setId(id);
        dto.setMatchId(matchId);
        dto.setTeamId(teamId);
        dto.setFormation("1-4-2-3-1");
        dto.setStarterIds(starters);
        dto.setSubstituteIds(substitutes);
        dto.setConfirmed(true);

        assertEquals(id, dto.getId());
        assertEquals(matchId, dto.getMatchId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals("1-4-2-3-1", dto.getFormation());
        assertEquals(starters, dto.getStarterIds());
        assertEquals(substitutes, dto.getSubstituteIds());
        assertTrue(dto.isConfirmed());
    }

    // ───────── StandingsResponseDTO ─────────
    @Test
    void testStandingsResponseDTO() {
        StandingsResponseDTO dto = new StandingsResponseDTO();
        String id = UUID.randomUUID().toString();
        String tournamentId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();

        dto.setId(id);
        dto.setTournamentId(tournamentId);
        dto.setTeamId(teamId);
        dto.setMatchesPlayed(10);
        dto.setMatchesWon(6);
        dto.setMatchesDrawn(2);
        dto.setMatchesLost(2);
        dto.setGoalsFor(20);
        dto.setGoalsAgainst(10);
        dto.setGoalDifference(10);
        dto.setPoints(20);

        assertEquals(id, dto.getId());
        assertEquals(tournamentId, dto.getTournamentId());
        assertEquals(teamId, dto.getTeamId());
        assertEquals(10, dto.getMatchesPlayed());
        assertEquals(6, dto.getMatchesWon());
        assertEquals(2, dto.getMatchesDrawn());
        assertEquals(2, dto.getMatchesLost());
        assertEquals(20, dto.getGoalsFor());
        assertEquals(10, dto.getGoalsAgainst());
        assertEquals(10, dto.getGoalDifference());
        assertEquals(20, dto.getPoints());
    }
}