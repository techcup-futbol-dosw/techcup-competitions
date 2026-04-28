package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MatchEventTest {

    private Goal goal;
    private Goal ownGoal;
    private Goal goalWithAssist;
    private Card yellowCard;
    private Card redCard;

    @BeforeEach
    void setUp() {
        goal = new Goal();
        goal.setMatchId(UUID.randomUUID());
        goal.setTeamId(UUID.randomUUID());
        goal.setPlayerId(UUID.randomUUID());
        goal.setMinute(45);
        goal.setOwnGoal(false);

        ownGoal = new Goal();
        ownGoal.setMinute(30);
        ownGoal.setOwnGoal(true);

        goalWithAssist = new Goal();
        goalWithAssist.setMinute(60);
        goalWithAssist.setOwnGoal(false);
        goalWithAssist.setAssistPlayerId(UUID.randomUUID());

        yellowCard = new Card();
        yellowCard.setMinute(20);
        yellowCard.setCardType(CardType.YELLOW);

        redCard = new Card();
        redCard.setMinute(75);
        redCard.setCardType(CardType.RED);
    }

    @Test
    void testGoalDescription() {
        String description = goal.getDescription();
        assertTrue(description.contains("Goal"));
        assertTrue(description.contains("45"));
    }

    @Test
    void testOwnGoalDescription() {
        String description = ownGoal.getDescription();
        assertTrue(description.contains("Own goal"));
        assertTrue(description.contains("30"));
    }

    @Test
    void testGoalWithAssistDescription() {
        String description = goalWithAssist.getDescription();
        assertTrue(description.contains("assisted by"));
    }

    @Test
    void testYellowCardDescription() {
        String description = yellowCard.getDescription();
        assertTrue(description.contains("YELLOW"));
        assertTrue(description.contains("20"));
    }

    @Test
    void testRedCardDescription() {
        String description = redCard.getDescription();
        assertTrue(description.contains("RED"));
        assertTrue(description.contains("75"));
    }

    @Test
    void testGoalIsNotOwnGoal() {
        assertFalse(goal.isOwnGoal());
    }

    @Test
    void testOwnGoalIsOwnGoal() {
        assertTrue(ownGoal.isOwnGoal());
    }

    @Test
    void testGoalGettersAndSetters() {
        UUID id = UUID.randomUUID();
        UUID matchId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        UUID assistPlayerId = UUID.randomUUID();

        goal.setId(id);
        goal.setMatchId(matchId);
        goal.setTeamId(teamId);
        goal.setPlayerId(playerId);
        goal.setMinute(90);
        goal.setAssistPlayerId(assistPlayerId);
        goal.setOwnGoal(false);

        assertEquals(id, goal.getId());
        assertEquals(matchId, goal.getMatchId());
        assertEquals(teamId, goal.getTeamId());
        assertEquals(playerId, goal.getPlayerId());
        assertEquals(90, goal.getMinute());
        assertEquals(assistPlayerId, goal.getAssistPlayerId());
        assertFalse(goal.isOwnGoal());
    }

    @Test
    void testCardGettersAndSetters() {
        UUID id = UUID.randomUUID();
        UUID matchId = UUID.randomUUID();
        UUID teamId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();

        yellowCard.setId(id);
        yellowCard.setMatchId(matchId);
        yellowCard.setTeamId(teamId);
        yellowCard.setPlayerId(playerId);
        yellowCard.setMinute(55);
        yellowCard.setCardType(CardType.RED);

        assertEquals(id, yellowCard.getId());
        assertEquals(matchId, yellowCard.getMatchId());
        assertEquals(teamId, yellowCard.getTeamId());
        assertEquals(playerId, yellowCard.getPlayerId());
        assertEquals(55, yellowCard.getMinute());
        assertEquals(CardType.RED, yellowCard.getCardType());
    }
}