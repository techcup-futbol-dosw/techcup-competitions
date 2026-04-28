package edu.eci.dosw.competitions.dtos;

import java.util.UUID;

public class StandingsResponseDTO {

    private UUID id;
    private UUID tournamentId;
    private UUID teamId;
    private int matchesPlayed;
    private int matchesWon;
    private int matchesDrawn;
    private int matchesLost;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getTournamentId() { return tournamentId; }
    public void setTournamentId(UUID tournamentId) { this.tournamentId = tournamentId; }

    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }

    public int getMatchesPlayed() { return matchesPlayed; }
    public void setMatchesPlayed(int matchesPlayed) { this.matchesPlayed = matchesPlayed; }

    public int getMatchesWon() { return matchesWon; }
    public void setMatchesWon(int matchesWon) { this.matchesWon = matchesWon; }

    public int getMatchesDrawn() { return matchesDrawn; }
    public void setMatchesDrawn(int matchesDrawn) { this.matchesDrawn = matchesDrawn; }

    public int getMatchesLost() { return matchesLost; }
    public void setMatchesLost(int matchesLost) { this.matchesLost = matchesLost; }

    public int getGoalsFor() { return goalsFor; }
    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }

    public int getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public int getGoalDifference() { return goalDifference; }
    public void setGoalDifference(int goalDifference) { this.goalDifference = goalDifference; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}