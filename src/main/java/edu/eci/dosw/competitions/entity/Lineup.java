package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lineups")
public class Lineup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID matchId;

    @Column(nullable = false)
    private UUID teamId;

    private String formation;

    @ElementCollection
    private List<UUID> starterIds;

    @ElementCollection
    private List<UUID> substituteIds;

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
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
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
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
