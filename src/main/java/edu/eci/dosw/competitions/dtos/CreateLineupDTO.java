package edu.eci.dosw.competitions.dtos;

import java.util.List;

public class CreateLineupDTO {

    private String matchId;
    private String teamId;
    private String formation;
    private List<String> starterIds;
    private List<String> substituteIds;

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getTeamId() { return teamId; }
    public void setTeamId(String teamId) { this.teamId = teamId; }

    public String getFormation() { return formation; }
    public void setFormation(String formation) { this.formation = formation; }

    public List<String> getStarterIds() { return starterIds; }
    public void setStarterIds(List<String> starterIds) { this.starterIds = starterIds; }

    public List<String> getSubstituteIds() { return substituteIds; }
    public void setSubstituteIds(List<String> substituteIds) { this.substituteIds = substituteIds; }
}