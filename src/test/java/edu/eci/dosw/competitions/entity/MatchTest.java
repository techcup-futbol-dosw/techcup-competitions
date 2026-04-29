package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MatchTest {

    private Match match;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);
    }

    // ===== UNIT TESTS =====

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
        String id = UUID.randomUUID().toString();
        String tournamentId = UUID.randomUUID().toString();
        String homeTeamId = UUID.randomUUID().toString();
        String awayTeamId = UUID.randomUUID().toString();
        String refereeId = UUID.randomUUID().toString();
        String fieldId = UUID.randomUUID().toString();
        LocalDateTime scheduledAt = LocalDateTime.now();

        match.setId(id);
        match.setTournamentId(tournamentId);
        match.setHomeTeamId(homeTeamId);
        match.setAwayTeamId(awayTeamId);
        match.setRefereeId(refereeId);
        match.setFieldId(fieldId);
        match.setScheduledAt(scheduledAt);
        match.setHomeScore(2);
        match.setAwayScore(1);
        match.setPhase(MatchPhase.FINAL);

        assertEquals(id, match.getId());
        assertEquals(tournamentId, match.getTournamentId());
        assertEquals(homeTeamId, match.getHomeTeamId());
        assertEquals(awayTeamId, match.getAwayTeamId());
        assertEquals(refereeId, match.getRefereeId());
        assertEquals(fieldId, match.getFieldId());
        assertEquals(scheduledAt, match.getScheduledAt());
        assertEquals(2, match.getHomeScore());
        assertEquals(1, match.getAwayScore());
        assertEquals(MatchPhase.FINAL, match.getPhase());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testMatchLifecycleIntegration() {
        Match match = new Match();
        match.setTournamentId(UUID.randomUUID().toString());
        match.setHomeTeamId(UUID.randomUUID().toString());
        match.setAwayTeamId(UUID.randomUUID().toString());
        match.setScheduledAt(LocalDateTime.now());
        match.setPhase(MatchPhase.GROUP_STAGE);
        match.setStatus(MatchStatus.SCHEDULED);

        assertTrue(match.canBeModified());
        assertTrue(match.canBeDeleted());

        match.start();
        assertEquals(MatchStatus.SCHEDULED, match.getStatus());

        match.finish();
        assertEquals(MatchStatus.FINISHED, match.getStatus());
        assertFalse(match.canBeModified());
        assertFalse(match.canBeDeleted());
    }

    @Test
    void testMatchWithAuditIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        MatchAudit audit = new MatchAudit();
        audit.setMatchId(match.getId());
        audit.setAction(MatchAuditAction.CREATED);
        audit.setDetail("Match created");
        audit.setTimestamp(LocalDateTime.now());

        match.finish();
        audit.setAction(MatchAuditAction.UPDATED);
        audit.setDetail("Match finished");

        assertEquals(MatchStatus.FINISHED, match.getStatus());
        assertEquals(MatchAuditAction.UPDATED, audit.getAction());
        assertTrue(audit.getDescription().contains("UPDATED"));
    }

    @Test
    void testCancelledMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        MatchAudit audit = new MatchAudit();
        audit.setAction(MatchAuditAction.CREATED);
        audit.setTimestamp(LocalDateTime.now());

        assertTrue(match.canBeDeleted());
        match.cancel();

        audit.setAction(MatchAuditAction.DELETED);
        audit.setDetail("Match cancelled");

        assertEquals(MatchStatus.CANCELLED, match.getStatus());
        assertFalse(match.canBeModified());
        assertTrue(audit.getDescription().contains("DELETED"));
    }
}