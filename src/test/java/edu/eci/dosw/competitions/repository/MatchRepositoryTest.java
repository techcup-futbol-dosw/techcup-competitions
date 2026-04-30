package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Match;
import edu.eci.dosw.competitions.entity.MatchPhase;
import edu.eci.dosw.competitions.entity.MatchStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MatchRepositoryTest {


    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp() {
        Match match = new Match();
        match.setId("match-1");
        match.setTournamentId("tournament-1");
        match.setHomeTeamId("team-home");
        match.setAwayTeamId("team-away");
        match.setStatus(MatchStatus.SCHEDULED);
        match.setPhase(MatchPhase.GROUP_STAGE);
        match.setScheduledAt(LocalDateTime.now().plusDays(1));
        matchRepository.save(match);
    }

    // ===== UNIT TESTS =====

    @Test
    void testFindById() {
        Optional<Match> result = matchRepository.findById("match-1");
        assertTrue(result.isPresent());
        assertEquals("tournament-1", result.get().getTournamentId());
    }

    @Test
    void testDeleteById() {
        matchRepository.deleteById("match-1");
        assertFalse(matchRepository.findById("match-1").isPresent());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFindByTournamentId() {
        List<Match> result = matchRepository.findByTournamentId("tournament-1");
        assertEquals(1, result.size());
    }

    @Test
    void testFindByTournamentId_notFound() {
        List<Match> result = matchRepository.findByTournamentId("tournament-999");
        assertTrue(result.isEmpty());
    }
}
