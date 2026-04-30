package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Standings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StandingsRepositoryTest {

    @Autowired
    private StandingsRepository standingsRepository;

    @BeforeEach
    void setUp() {
        Standings s1 = new Standings();
        s1.setId("standings-1");
        s1.setTournamentId("tournament-1");
        s1.setTeamId("team-1");
        s1.setPoints(9);
        s1.setGoalDifference(5);

        Standings s2 = new Standings();
        s2.setId("standings-2");
        s2.setTournamentId("tournament-1");
        s2.setTeamId("team-2");
        s2.setPoints(6);
        s2.setGoalDifference(2);

        standingsRepository.save(s1);
        standingsRepository.save(s2);
    }

    // ===== UNIT TESTS =====

    @Test
    void testFindByTournamentIdAndTeamId() {
        Optional<Standings> result = standingsRepository.findByTournamentIdAndTeamId("tournament-1", "team-1");
        assertTrue(result.isPresent());
        assertEquals(9, result.get().getPoints());
    }

    @Test
    void testFindByTournamentIdAndTeamId_notFound() {
        Optional<Standings> result = standingsRepository.findByTournamentIdAndTeamId("tournament-1", "team-999");
        assertTrue(result.isEmpty());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFindByTournamentId() {
        List<Standings> result = standingsRepository.findByTournamentId("tournament-1");
        assertEquals(2, result.size());
    }

    @Test
    void testFindRankedByTournamentId() {
        List<Standings> result = standingsRepository
                .findByTournamentIdOrderByPointsDescGoalDifferenceDesc("tournament-1");
        assertEquals(2, result.size());
        assertTrue(result.get(0).getPoints() >= result.get(1).getPoints());
    }
}