package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class StandingsTest {

    private Standings standings;

    @BeforeEach
    void setUp() {
        standings = new Standings();
        standings.setTournamentId(UUID.randomUUID().toString());
        standings.setTeamId(UUID.randomUUID().toString());
    }

    @Test
    void testUpdateAfterMatchWin() {
        standings.updateAfterMatch(3, 1);
        assertEquals(1, standings.getMatchesPlayed());
        assertEquals(1, standings.getMatchesWon());
        assertEquals(0, standings.getMatchesDrawn());
        assertEquals(0, standings.getMatchesLost());
        assertEquals(3, standings.getGoalsFor());
        assertEquals(1, standings.getGoalsAgainst());
        assertEquals(3, standings.getPoints());
        assertEquals(2, standings.getGoalDifference());
    }

    @Test
    void testUpdateAfterMatchDraw() {
        standings.updateAfterMatch(1, 1);
        assertEquals(1, standings.getMatchesPlayed());
        assertEquals(0, standings.getMatchesWon());
        assertEquals(1, standings.getMatchesDrawn());
        assertEquals(0, standings.getMatchesLost());
        assertEquals(1, standings.getPoints());
        assertEquals(0, standings.getGoalDifference());
    }

    @Test
    void testUpdateAfterMatchLoss() {
        standings.updateAfterMatch(0, 2);
        assertEquals(1, standings.getMatchesPlayed());
        assertEquals(0, standings.getMatchesWon());
        assertEquals(0, standings.getMatchesDrawn());
        assertEquals(1, standings.getMatchesLost());
        assertEquals(0, standings.getPoints());
        assertEquals(-2, standings.getGoalDifference());
    }

    @Test
    void testCalculatePoints() {
        standings.setMatchesWon(3);
        standings.setMatchesDrawn(2);
        standings.calculatePoints();
        assertEquals(11, standings.getPoints());
    }

    @Test
    void testCalculateGoalDifference() {
        standings.setGoalsFor(10);
        standings.setGoalsAgainst(5);
        int diff = standings.calculateGoalDifference();
        assertEquals(5, diff);
        assertEquals(5, standings.getGoalDifference());
    }

    @Test
    void testCalculateNegativeGoalDifference() {
        standings.setGoalsFor(3);
        standings.setGoalsAgainst(8);
        int diff = standings.calculateGoalDifference();
        assertEquals(-5, diff);
    }

    @Test
    void testGettersAndSetters() {
        String id = UUID.randomUUID().toString();
        String tournamentId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();

        standings.setId(id);
        standings.setTournamentId(tournamentId);
        standings.setTeamId(teamId);
        standings.setMatchesPlayed(10);
        standings.setMatchesWon(5);
        standings.setMatchesDrawn(3);
        standings.setMatchesLost(2);
        standings.setGoalsFor(15);
        standings.setGoalsAgainst(8);
        standings.setGoalDifference(7);
        standings.setPoints(18);

        assertEquals(id, standings.getId());
        assertEquals(tournamentId, standings.getTournamentId());
        assertEquals(teamId, standings.getTeamId());
        assertEquals(10, standings.getMatchesPlayed());
        assertEquals(5, standings.getMatchesWon());
        assertEquals(3, standings.getMatchesDrawn());
        assertEquals(2, standings.getMatchesLost());
        assertEquals(15, standings.getGoalsFor());
        assertEquals(8, standings.getGoalsAgainst());
        assertEquals(7, standings.getGoalDifference());
        assertEquals(18, standings.getPoints());
    }

    @Test
    void testMultipleMatchesIntegration() {
        standings.updateAfterMatch(2, 0);
        standings.updateAfterMatch(1, 1);
        standings.updateAfterMatch(0, 3);

        assertEquals(3, standings.getMatchesPlayed());
        assertEquals(1, standings.getMatchesWon());
        assertEquals(1, standings.getMatchesDrawn());
        assertEquals(1, standings.getMatchesLost());
        assertEquals(3, standings.getGoalsFor());
        assertEquals(4, standings.getGoalsAgainst());
        assertEquals(4, standings.getPoints());
        assertEquals(-1, standings.getGoalDifference());
    }

    @Test
    void testPerfectSeasonIntegration() {
        standings.updateAfterMatch(3, 0);
        standings.updateAfterMatch(2, 0);
        standings.updateAfterMatch(1, 0);

        assertEquals(3, standings.getMatchesPlayed());
        assertEquals(3, standings.getMatchesWon());
        assertEquals(0, standings.getMatchesDrawn());
        assertEquals(0, standings.getMatchesLost());
        assertEquals(9, standings.getPoints());
        assertEquals(6, standings.getGoalDifference());
    }

    @Test
    void testStandingsWithTournamentIntegration() {
        String tournamentId = UUID.randomUUID().toString();
        String teamId = UUID.randomUUID().toString();

        standings.setTournamentId(tournamentId);
        standings.setTeamId(teamId);

        standings.updateAfterMatch(2, 1);
        standings.updateAfterMatch(0, 0);

        assertEquals(tournamentId, standings.getTournamentId());
        assertEquals(teamId, standings.getTeamId());
        assertEquals(2, standings.getMatchesPlayed());
        assertEquals(4, standings.getPoints());
    }
}