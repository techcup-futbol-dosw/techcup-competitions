package edu.eci.dosw.competitions.repository;

import edu.eci.dosw.competitions.entity.Card;
import edu.eci.dosw.competitions.entity.CardType;
import edu.eci.dosw.competitions.entity.Goal;
import edu.eci.dosw.competitions.entity.MatchEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MatchEventRepositoryTest {

    @Autowired
    private MatchEventRepository matchEventRepository;

    @BeforeEach
    void setUp() {
        Goal goal = new Goal();
        goal.setId("goal-1");
        goal.setMatchId("match-1");
        goal.setTeamId("team-1");
        goal.setPlayerId("player-1");
        goal.setMinute(30);
        goal.setOwnGoal(false);

        Card card = new Card();
        card.setId("card-1");
        card.setMatchId("match-1");
        card.setTeamId("team-1");
        card.setPlayerId("player-2");
        card.setMinute(45);
        card.setCardType(CardType.YELLOW);

        matchEventRepository.save(goal);
        matchEventRepository.save(card);
    }

    // ===== UNIT TESTS =====

    @Test
    void testFindGoalsByMatchId() {
        List<Goal> result = matchEventRepository.findGoalsByMatchId("match-1");
        assertEquals(1, result.size());
        assertEquals("goal-1", result.get(0).getId());
    }

    @Test
    void testFindCardsByMatchId() {
        List<Card> result = matchEventRepository.findCardsByMatchId("match-1");
        assertEquals(1, result.size());
        assertEquals(CardType.YELLOW, result.get(0).getCardType());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFindByMatchId_returnsAllEvents() {
        List<MatchEvent> result = matchEventRepository.findByMatchId("match-1");
        assertEquals(2, result.size());
    }

    @Test
    void testFindByMatchId_notFound() {
        List<MatchEvent> result = matchEventRepository.findByMatchId("match-999");
        assertTrue(result.isEmpty());
    }
}