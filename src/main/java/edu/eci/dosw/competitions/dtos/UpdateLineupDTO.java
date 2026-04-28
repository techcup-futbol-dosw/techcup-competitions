package edu.eci.dosw.competitions.dtos;

import java.util.List;
import java.util.UUID;

public class UpdateLineupDTO {

    private String formation;
    private List<UUID> starterIds;
    private List<UUID> substituteIds;

    public String getFormation() { return formation; }
    public void setFormation(String formation) { this.formation = formation; }

    public List<UUID> getStarterIds() { return starterIds; }
    public void setStarterIds(List<UUID> starterIds) { this.starterIds = starterIds; }

    public List<UUID> getSubstituteIds() { return substituteIds; }
    public void setSubstituteIds(List<UUID> substituteIds) { this.substituteIds = substituteIds; }
}
