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

    // ===== UNIT TESTS =====

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

    // ===== INTEGRATION TESTS =====

    @Test
    void testGoalAndCardInMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);
        match.start();

        Goal matchGoal = new Goal();
        matchGoal.setMatchId(match.getId());
        matchGoal.setTeamId(UUID.randomUUID());
        matchGoal.setPlayerId(UUID.randomUUID());
        matchGoal.setMinute(35);
        matchGoal.setOwnGoal(false);

        Card matchCard = new Card();
        matchCard.setMatchId(match.getId());
        matchCard.setTeamId(UUID.randomUUID());
        matchCard.setPlayerId(UUID.randomUUID());
        matchCard.setMinute(40);
        matchCard.setCardType(CardType.YELLOW);

        assertTrue(matchGoal.getDescription().contains("Goal"));
        assertTrue(matchGoal.getDescription().contains("35"));
        assertTrue(matchCard.getDescription().contains("YELLOW"));
        assertTrue(matchCard.getDescription().contains("40"));
    }

    @Test
    void testMultipleEventsInMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        Goal goal1 = new Goal();
        goal1.setMatchId(match.getId());
        goal1.setMinute(10);
        goal1.setOwnGoal(false);

        Goal goal2 = new Goal();
        goal2.setMatchId(match.getId());
        goal2.setMinute(55);
        goal2.setOwnGoal(true);

        Card card1 = new Card();
        card1.setMatchId(match.getId());
        card1.setMinute(70);
        card1.setCardType(CardType.RED);

        assertTrue(goal1.getDescription().contains("Goal"));
        assertTrue(goal2.getDescription().contains("Own goal"));
        assertTrue(card1.getDescription().contains("RED"));

        match.finish();
        assertEquals(MatchStatus.FINISHED, match.getStatus());
    }

    @Test
    void testOwnGoalWithMatchIntegration() {
        Match match = new Match();
        match.setStatus(MatchStatus.SCHEDULED);

        Goal ownGoalEvent = new Goal();
        ownGoalEvent.setMatchId(match.getId());
        ownGoalEvent.setTeamId(UUID.randomUUID());
        ownGoalEvent.setPlayerId(UUID.randomUUID());
        ownGoalEvent.setMinute(88);
        ownGoalEvent.setOwnGoal(true);

        assertTrue(ownGoalEvent.isOwnGoal());
        assertTrue(ownGoalEvent.getDescription().contains("Own goal"));
        assertTrue(ownGoalEvent.getDescription().contains("88"));
        assertEquals(MatchStatus.SCHEDULED, match.getStatus());
    }
}
