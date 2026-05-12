package edu.eci.dosw.competitions.model;

import edu.eci.dosw.competitions.entity.Match;
import edu.eci.dosw.competitions.entity.MatchPhase;
import edu.eci.dosw.competitions.entity.MatchStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchModelTest {

    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId("match-1");
        match.setTournamentId("tournament-1");
        match.setHomeTeamId("home-1");
        match.setAwayTeamId("away-1");
        match.setScheduledAt(LocalDateTime.now());
        match.setPhase(MatchPhase.GROUP_STAGE);
        match.setHomeScore(0);
        match.setAwayScore(0);
    }

    // ===== UNIT TESTS =====

    @Test
    void testScheduledMatch_canStart() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        model.start();

        assertEquals(MatchStatus.IN_PROGRESS, match.getStatus());
    }

    @Test
    void testScheduledMatch_canCancel() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        model.cancel();

        assertEquals(MatchStatus.CANCELLED, match.getStatus());
    }

    @Test
    void testScheduledMatch_canModify() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        assertDoesNotThrow(model::modify);
    }

    @Test
    void testScheduledMatch_canDelete() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        assertDoesNotThrow(model::delete);
    }

    @Test
    void testScheduledMatch_cannotFinish() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::finish);
    }

    @Test
    void testScheduledMatch_cannotRegisterGoal() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::registerGoal);
    }

    @Test
    void testScheduledMatch_cannotRegisterCard() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::registerCard);
    }

    @Test
    void testInProgressMatch_canFinish() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        model.finish();

        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testInProgressMatch_canRegisterGoal() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        assertDoesNotThrow(model::registerGoal);
    }

    @Test
    void testInProgressMatch_canRegisterCard() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        assertDoesNotThrow(model::registerCard);
    }

    @Test
    void testInProgressMatch_cannotStart() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::start);
    }

    @Test
    void testInProgressMatch_cannotCancel() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::cancel);
    }

    @Test
    void testInProgressMatch_cannotDelete() {
        match.setStatus(MatchStatus.IN_PROGRESS);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::delete);
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFinishedMatch_cannotDoAnything() {
        match.setStatus(MatchStatus.FINISHED);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::start);
        assertThrows(RuntimeException.class, model::finish);
        assertThrows(RuntimeException.class, model::cancel);
        assertThrows(RuntimeException.class, model::registerGoal);
        assertThrows(RuntimeException.class, model::registerCard);
        assertThrows(RuntimeException.class, model::modify);
        assertThrows(RuntimeException.class, model::delete);
    }

    @Test
    void testCancelledMatch_cannotDoAnything() {
        match.setStatus(MatchStatus.CANCELLED);
        MatchModel model = new MatchModel(match);

        assertThrows(RuntimeException.class, model::start);
        assertThrows(RuntimeException.class, model::finish);
        assertThrows(RuntimeException.class, model::cancel);
        assertThrows(RuntimeException.class, model::registerGoal);
        assertThrows(RuntimeException.class, model::registerCard);
        assertThrows(RuntimeException.class, model::modify);
        assertThrows(RuntimeException.class, model::delete);
    }

    @Test
    void testFullLifecycle_scheduledToInProgressToFinished() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        model.start();
        assertEquals(MatchStatus.IN_PROGRESS, match.getStatus());

        assertDoesNotThrow(model::registerGoal);
        assertDoesNotThrow(model::registerCard);

        model.finish();
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testFullLifecycle_scheduledToCancelled() {
        match.setStatus(MatchStatus.SCHEDULED);
        MatchModel model = new MatchModel(match);

        model.cancel();
        assertEquals(MatchStatus.CANCELLED, match.getStatus());

        assertThrows(RuntimeException.class, model::start);
        assertThrows(RuntimeException.class, model::finish);
    }

    @Test
    void testStateFactory_createsCorrectStateForEachStatus() {
        for (MatchStatus status : MatchStatus.values()) {
            match.setStatus(status);
            MatchModel model = new MatchModel(match);
            assertEquals(status, model.getStatus());
        }
    }
}