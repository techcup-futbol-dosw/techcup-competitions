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
        // Gol normal
        goal = new Goal();
        goal.setMatchId(UUID.randomUUID());
        goal.setTeamId(UUID.randomUUID());
        goal.setPlayerId(UUID.randomUUID());
        goal.setMinute(45);
        goal.setOwnGoal(false);

        // Autogol
        ownGoal = new Goal();
        ownGoal.setMinute(30);
        ownGoal.setOwnGoal(true);

        // Gol con asistencia
        goalWithAssist = new Goal();
        goalWithAssist.setMinute(60);
        goalWithAssist.setOwnGoal(false);
        goalWithAssist.setAssistPlayerId(UUID.randomUUID());

        // Tarjeta amarilla
        yellowCard = new Card();
        yellowCard.setMinute(20);
        yellowCard.setCardType(CardType.YELLOW);

        // Tarjeta roja
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
}
