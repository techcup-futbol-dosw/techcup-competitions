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
}
