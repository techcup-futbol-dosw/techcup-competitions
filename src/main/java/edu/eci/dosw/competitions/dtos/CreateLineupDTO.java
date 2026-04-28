package edu.eci.dosw.competitions.dtos;

import java.util.List;
import java.util.UUID;

public class CreateLineupDTO {

    private UUID matchId;
    private UUID teamId;
    private String formation;
    private List<UUID> starterIds;
    private List<UUID> substituteIds;

    public UUID getMatchId() { return matchId; }
    public void setMatchId(UUID matchId) { this.matchId = matchId; }

    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }

    public String getFormation() { return formation; }
    public void setFormation(String formation) { this.formation = formation; }

    public List<UUID> getStarterIds() { return starterIds; }
    public void setStarterIds(List<UUID> starterIds) { this.starterIds = starterIds; }

    public List<UUID> getSubstituteIds() { return substituteIds; }
    public void setSubstituteIds(List<UUID> substituteIds) { this.substituteIds = substituteIds; }
}