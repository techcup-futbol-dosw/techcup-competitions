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
        matchAudit.setMatchId(UUID.randomUUID());
        matchAudit.setAction(MatchAuditAction.CREATED);
        matchAudit.setDetail("Match created");
        matchAudit.setTimestamp(LocalDateTime.of(2024, 1, 15, 10, 30));
    }

    @Test
    void testGetDescriptionContainsAction() {
        String description = matchAudit.getDescription();
        assertTrue(description.contains("CREATED"));
    }

    @Test
    void testGetDescriptionContainsDetail() {
        String description = matchAudit.getDescription();
        assertTrue(description.contains("Match created"));
    }

    @Test
    void testGetDescriptionContainsTimestamp() {
        String description = matchAudit.getDescription();
        assertTrue(description.contains("2024-01-15"));
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
        String description = matchAudit.getDescription();
        assertTrue(description.contains("DELETED"));
    }
}
