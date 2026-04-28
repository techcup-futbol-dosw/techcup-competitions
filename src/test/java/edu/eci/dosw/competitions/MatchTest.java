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

    @Test
    void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        UUID tournamentId = UUID.randomUUID();
        UUID homeTeamId = UUID.randomUUID();
        UUID awayTeamId = UUID.randomUUID();
        LocalDateTime scheduledAt = LocalDateTime.now();

        match.setId(id);
        match.setTournamentId(tournamentId);
        match.setHomeTeamId(homeTeamId);
        match.setAwayTeamId(awayTeamId);
        match.setScheduledAt(scheduledAt);
        match.setHomeScore(2);
        match.setAwayScore(1);
        match.setPhase(MatchPhase.FINAL);

        assertEquals(id, match.getId());
        assertEquals(tournamentId, match.getTournamentId());
        assertEquals(homeTeamId, match.getHomeTeamId());
        assertEquals(awayTeamId, match.getAwayTeamId());
        assertEquals(scheduledAt, match.getScheduledAt());
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
        assertEquals(MatchPhase.FINAL, match.getPhase());
    }
}
