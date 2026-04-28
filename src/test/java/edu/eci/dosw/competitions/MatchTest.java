package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);
    }

    @Test
    void testStart() {
        match.start();
        assertEquals(MatchStatus.SCHEDULED, match.getStatus());
    }

    @Test
    void testFinish() {
        match.finish();
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testCancel() {
        match.cancel();
        assertEquals(MatchStatus.CANCELLED, match.getStatus());
    }

    @Test
    void testCanBeModifiedWhenScheduled() {
        match.setStatus(MatchStatus.SCHEDULED);
        assertTrue(match.canBeModified());
    }

    @Test
    void testCanBeModifiedWhenFinished() {
        match.setStatus(MatchStatus.FINISHED);
        assertFalse(match.canBeModified());
    }

    @Test
    void testCanBeDeletedWhenScheduled() {
        match.setStatus(MatchStatus.SCHEDULED);
        assertTrue(match.canBeDeleted());
    }

    @Test
    void testCanBeDeletedWhenCancelled() {
        match.setStatus(MatchStatus.CANCELLED);
        assertFalse(match.canBeDeleted());
    }
}
