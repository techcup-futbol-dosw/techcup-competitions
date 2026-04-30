package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lineups")
public class Lineup {

    @Id
    private String id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private String teamId;

    private String formation;

    @ElementCollection
    private List<String> starterIds;

    @ElementCollection
    private List<String> substituteIds;

    private boolean confirmed;

    public void confirm() {
        this.confirmed = true;
    }

    public boolean validateStarters() {
        return starterIds != null && starterIds.size() == 11;
    }

    public boolean validateGoalkeeper() {
        return starterIds != null && !starterIds.isEmpty();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
