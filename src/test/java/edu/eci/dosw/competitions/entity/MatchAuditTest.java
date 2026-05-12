package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MatchAuditTest {

    private MatchAudit matchAudit;

    @BeforeEach
    void setUp() {
        matchAudit = new MatchAudit();
        matchAudit.setMatchId(UUID.randomUUID().toString());
        matchAudit.setAction(MatchAuditAction.CREATED);
        matchAudit.setDetail("Match created");
        matchAudit.setTimestamp(LocalDateTime.of(2024, 1, 15, 10, 30));
    }

    @Test
    void testGetDescriptionContainsAction() {
        assertTrue(matchAudit.getDescription().contains("CREATED"));
    }

    @Test
    void testGetDescriptionContainsDetail() {
        assertTrue(matchAudit.getDescription().contains("Match created"));
    }

    @Test
    void testGetDescriptionContainsTimestamp() {
        assertTrue(matchAudit.getDescription().contains("2024-01-15"));
    }

    @Test
    void testGetDescriptionWithUpdatedAction() {
        matchAudit.setAction(MatchAuditAction.UPDATED);
        matchAudit.setDetail("Score updated");
        String description = matchAudit.getDescription();
        assertTrue(description.contains("UPDATED"));
        assertTrue(description.contains("Score updated"));
    }

    @Test
    void testGetDescriptionWithDeletedAction() {
        matchAudit.setAction(MatchAuditAction.DELETED);
        assertTrue(matchAudit.getDescription().contains("DELETED"));
    }

    @Test
    void testGettersAndSetters() {
        String id = UUID.randomUUID().toString();
        String matchId = UUID.randomUUID().toString();
        LocalDateTime timestamp = LocalDateTime.now();

        matchAudit.setId(id);
        matchAudit.setMatchId(matchId);
        matchAudit.setAction(MatchAuditAction.UPDATED);
        matchAudit.setDetail("Some detail");
        matchAudit.setTimestamp(timestamp);

        assertEquals(id, matchAudit.getId());
        assertEquals(matchId, matchAudit.getMatchId());
        assertEquals(MatchAuditAction.UPDATED, matchAudit.getAction());
        assertEquals("Some detail", matchAudit.getDetail());
        assertEquals(timestamp, matchAudit.getTimestamp());
    }

    @Test
    void testAuditLifecycleIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        MatchAudit createAudit = new MatchAudit();
        createAudit.setMatchId(match.getId());
        createAudit.setAction(MatchAuditAction.CREATED);
        createAudit.setDetail("Match created");
        createAudit.setTimestamp(LocalDateTime.now());

        assertTrue(createAudit.getDescription().contains("CREATED"));

        match.finish();

        MatchAudit updateAudit = new MatchAudit();
        updateAudit.setMatchId(match.getId());
        updateAudit.setAction(MatchAuditAction.UPDATED);
        updateAudit.setDetail("Match finished");
        updateAudit.setTimestamp(LocalDateTime.now());

        assertTrue(updateAudit.getDescription().contains("UPDATED"));
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testMultipleAuditsIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        MatchAudit audit1 = new MatchAudit();
        audit1.setMatchId(match.getId());
        audit1.setAction(MatchAuditAction.CREATED);
        audit1.setDetail("Created");
        audit1.setTimestamp(LocalDateTime.now());

        MatchAudit audit2 = new MatchAudit();
        audit2.setMatchId(match.getId());
        audit2.setAction(MatchAuditAction.UPDATED);
        audit2.setDetail("Updated score");
        audit2.setTimestamp(LocalDateTime.now());

        MatchAudit audit3 = new MatchAudit();
        audit3.setMatchId(match.getId());
        audit3.setAction(MatchAuditAction.DELETED);
        audit3.setDetail("Deleted");
        audit3.setTimestamp(LocalDateTime.now());

        assertTrue(audit1.getDescription().contains("CREATED"));
        assertTrue(audit2.getDescription().contains("UPDATED"));
        assertTrue(audit3.getDescription().contains("DELETED"));
    }

    @Test
    void testAuditWithCancelledMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);
        match.cancel();

        MatchAudit audit = new MatchAudit();
        audit.setMatchId(match.getId());
        audit.setAction(MatchAuditAction.DELETED);
        audit.setDetail("Match cancelled");
        audit.setTimestamp(LocalDateTime.now());

        assertEquals(MatchStatus.CANCELLED, match.getStatus());
        assertTrue(audit.getDescription().contains("DELETED"));
        assertTrue(audit.getDescription().contains("Match cancelled"));
    }
}