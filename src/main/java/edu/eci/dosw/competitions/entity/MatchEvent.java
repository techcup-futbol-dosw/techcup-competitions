package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "match_events")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MatchEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID matchId;

    @Column(nullable = false)
    private UUID teamId;

    @Column(nullable = false)
    private UUID playerId;

    @Column(nullable = false)
    private int minute;

    public abstract String getDescription();

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getMatchId() { return matchId; }
    public void setMatchId(UUID matchId) { this.matchId = matchId; }
    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }
    public UUID getPlayerId() { return playerId; }
    public void setPlayerId(UUID playerId) { this.playerId = playerId; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}
