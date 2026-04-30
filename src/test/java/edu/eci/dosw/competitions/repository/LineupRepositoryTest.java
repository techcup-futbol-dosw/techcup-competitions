package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Lineup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineupRepositoryTest {

    @Autowired
    private LineupRepository lineupRepository;

    @BeforeEach
    void setUp() {
        Lineup lineup = new Lineup();
        lineup.setId("lineup-1");
        lineup.setMatchId("match-1");
        lineup.setTeamId("team-1");
        lineup.setFormation("4-3-3");
        lineupRepository.save(lineup);
    }

    // ===== UNIT TESTS =====

    @Test
    void testFindByMatchIdAndTeamId() {
        Optional<Lineup> result = lineupRepository.findByMatchIdAndTeamId("match-1", "team-1");
        assertTrue(result.isPresent());
        assertEquals("4-3-3", result.get().getFormation());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFindByMatchIdAndTeamId_notFound() {
        Optional<Lineup> result = lineupRepository.findByMatchIdAndTeamId("match-1", "team-999");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveAndFindById() {
        Optional<Lineup> result = lineupRepository.findById("lineup-1");
        assertTrue(result.isPresent());
        assertEquals("team-1", result.get().getTeamId());
    }
}