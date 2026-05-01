package edu.eci.dosw.competitions.model;

import edu.eci.dosw.competitions.entity.Lineup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineupModelTest {

    private Lineup lineup;

    @BeforeEach
    void setUp() {
        lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineup.setStarterIds(List.of("p1","p2","p3","p4","p5","p6","p7","p8","p9","p10","p11"));
        lineup.setSubstituteIds(List.of("p12","p13","p14"));
        lineup.setConfirmed(false);
    }

    // ===== UNIT TESTS =====

    @Test
    void testUnconfirmedLineup_canUpdate() {
        LineupModel model = new LineupModel(lineup);

        assertDoesNotThrow(model::update);
    }

    @Test
    void testUnconfirmedLineup_canConfirm() {
        LineupModel model = new LineupModel(lineup);

        model.confirm();

        assertTrue(lineup.isConfirmed());
    }

    @Test
    void testConfirmedLineup_cannotUpdate() {
        lineup.setConfirmed(true);
        LineupModel model = new LineupModel(lineup);

        assertThrows(RuntimeException.class, model::update);
    }

    @Test
    void testConfirmedLineup_cannotConfirmAgain() {
        lineup.setConfirmed(true);
        LineupModel model = new LineupModel(lineup);

        assertThrows(RuntimeException.class, model::confirm);
    }

    @Test
    void testUnconfirmedLineup_isNotConfirmed() {
        LineupModel model = new LineupModel(lineup);

        assertFalse(model.isConfirmed());
    }

    @Test
    void testConfirmedLineup_isConfirmed() {
        lineup.setConfirmed(true);
        LineupModel model = new LineupModel(lineup);

        assertTrue(model.isConfirmed());
    }

    @Test
    void testGetLineup_returnsCorrectEntity() {
        LineupModel model = new LineupModel(lineup);

        assertEquals(lineup, model.getLineup());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFullLifecycle_updateThenConfirm() {
        LineupModel model = new LineupModel(lineup);

        assertDoesNotThrow(model::update);
        assertFalse(lineup.isConfirmed());

        model.confirm();
        assertTrue(lineup.isConfirmed());
    }

    @Test
    void testFullLifecycle_confirmThenCannotUpdate() {
        LineupModel model = new LineupModel(lineup);

        model.confirm();
        assertTrue(lineup.isConfirmed());

        assertThrows(RuntimeException.class, model::update);
    }

    @Test
    void testFullLifecycle_confirmThenCannotConfirmAgain() {
        LineupModel model = new LineupModel(lineup);

        model.confirm();

        assertThrows(RuntimeException.class, model::confirm);
    }
}