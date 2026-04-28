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
        List<UUID> starters = Arrays.asList(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID()
        );
        lineup.setStarterIds(starters);
        assertTrue(lineup.validateStarters());
    }

    @Test
    void testValidateStartersWithLessThan11Players() {
        List<UUID> starters = Arrays.asList(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()
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
        lineup.setStarterIds(Arrays.asList(UUID.randomUUID()));
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
        UUID id = UUID.randomUUID();
        UUID matchId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        List<UUID> starters = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
        List<UUID> substitutes = Arrays.asList(UUID.randomUUID());

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
        UUID matchId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();

        lineup.setMatchId(matchId);
        lineup.setTeamId(teamId);
        lineup.setFormation("4-4-2");

        List<UUID> starters = Arrays.asList(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID()
        );
        lineup.setStarterIds(starters);
        lineup.setSubstituteIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

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
        lineup.setTeamId(UUID.randomUUID());

        List<UUID> starters = Arrays.asList(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), UUID.randomUUID()
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
        lineup.setMatchId(UUID.randomUUID());
        lineup.setTeamId(UUID.randomUUID());
        lineup.setStarterIds(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));

        assertFalse(lineup.validateStarters());
        assertTrue(lineup.validateGoalkeeper());
        assertFalse(lineup.isConfirmed());
    }
}
