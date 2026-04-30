package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class LineupTest {

    private Lineup lineup;

    @BeforeEach
    void setUp() {
        lineup = new Lineup();
        lineup.setConfirmed(false);
    }

    // ===== UNIT TESTS =====

    @Test
    void testConfirm() {
        lineup.confirm();
        assertTrue(lineup.isConfirmed());
    }

    @Test
    void testValidateStartersWithExactly11Players() {
        List<String> starters = Arrays.asList(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
        lineup.setStarterIds(starters);
        assertTrue(lineup.validateStarters());
    }

    @Test
    void testValidateStartersWithLessThan11Players() {
        List<String> starters = Arrays.asList(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
        lineup.setStarterIds(starters);
        assertFalse(lineup.validateStarters());
    }

    @Test
    void testValidateStartersWithNull() {
        lineup.setStarterIds(null);
        assertFalse(lineup.validateStarters());
    }

    @Test
    void testValidateGoalkeeperWithPlayers() {
        lineup.setStarterIds(Arrays.asList(UUID.randomUUID().toString()));
        assertTrue(lineup.validateGoalkeeper());
    }

    @Test
    void testValidateGoalkeeperWithEmptyList() {
        lineup.setStarterIds(Arrays.asList());
        assertFalse(lineup.validateGoalkeeper());
    }

    @Test
    void testValidateGoalkeeperWithNull() {
        lineup.setStarterIds(null);
        assertFalse(lineup.validateGoalkeeper());
    }

    @Test
    void testGettersAndSetters() {
        String id = UUID.randomUUID().toString();
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();
        List<String> starters = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        List<String> substitutes = Arrays.asList(UUID.randomUUID().toString());

        lineup.setId(id);
        lineup.setMatchId(matchId);
        lineup.setTeamId(teamId);
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(starters);
        lineup.setSubstituteIds(substitutes);
        lineup.setConfirmed(true);

        assertEquals(id, lineup.getId());
        assertEquals(matchId, lineup.getMatchId());
        assertEquals(teamId, lineup.getTeamId());
        assertEquals("4-3-3", lineup.getFormation());
        assertEquals(starters, lineup.getStarterIds());
        assertEquals(substitutes, lineup.getSubstituteIds());
        assertTrue(lineup.isConfirmed());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testLineupFullFlowIntegration() {
        String matchId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();

        lineup.setMatchId(matchId);
        lineup.setTeamId(teamId);
        lineup.setFormation("4-4-2");

        List<String> starters = Arrays.asList(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
        lineup.setStarterIds(starters);
        lineup.setSubstituteIds(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        assertTrue(lineup.validateStarters());
        assertTrue(lineup.validateGoalkeeper());
        assertFalse(lineup.isConfirmed());

        lineup.confirm();
        assertTrue(lineup.isConfirmed());
    }

    @Test
    void testLineupWithMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        lineup.setMatchId(match.getId());
        lineup.setTeamId(UUID.randomUUID().toString());

        List<String> starters = Arrays.asList(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
        lineup.setStarterIds(starters);

        assertTrue(match.canBeModified());
        assertTrue(lineup.validateStarters());

        lineup.confirm();
        match.finish();

        assertTrue(lineup.isConfirmed());
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testInvalidLineupIntegration() {
        lineup.setMatchId(UUID.randomUUID().toString());
        lineup.setTeamId(UUID.randomUUID().toString());
        lineup.setStarterIds(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        assertFalse(lineup.validateStarters());
        assertTrue(lineup.validateGoalkeeper());
        assertFalse(lineup.isConfirmed());
    }
}
