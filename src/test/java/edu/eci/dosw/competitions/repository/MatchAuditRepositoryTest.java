package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.MatchAudit;
import edu.eci.dosw.competitions.entity.MatchAuditAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MatchAuditRepositoryTest {

    @Autowired
    private MatchAuditRepository matchAuditRepository;

    @BeforeEach
    void setUp() {
        MatchAudit audit = new MatchAudit();
        audit.setId("audit-1");
        audit.setMatchId("match-1");
        audit.setAction(MatchAuditAction.CREATED);
        audit.setDetail("Match created");
        audit.setTimestamp(LocalDateTime.now());
        matchAuditRepository.save(audit);
    }

    // ===== UNIT TESTS =====

    @Test
    void testFindByMatchId() {
        List<MatchAudit> result = matchAuditRepository.findByMatchId("match-1");
        assertEquals(1, result.size());
        assertEquals(MatchAuditAction.CREATED, result.get(0).getAction());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFindByMatchId_notFound() {
        List<MatchAudit> result = matchAuditRepository.findByMatchId("match-999");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveAndFindById() {
        assertTrue(matchAuditRepository.findById("audit-1").isPresent());
    }
}