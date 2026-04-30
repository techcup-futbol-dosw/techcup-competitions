package edu.eci.dosw.competitions.dtos;

import java.util.List;

public class UpdateLineupDTO {

    private String formation;
    private List<String> starterIds;
    private List<String> substituteIds;

    public String getFormation() { return formation; }
    public void setFormation(String formation) { this.formation = formation; }

    public List<String> getStarterIds() { return starterIds; }
    public void setStarterIds(List<String> starterIds) { this.starterIds = starterIds; }

    public List<String> getSubstituteIds() { return substituteIds; }
    public void setSubstituteIds(List<String> substituteIds) { this.substituteIds = substituteIds; }
}
