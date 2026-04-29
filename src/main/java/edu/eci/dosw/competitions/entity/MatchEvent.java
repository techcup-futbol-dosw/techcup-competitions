package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "match_events")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MatchEvent {

    @Id
    private String id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private String teamId;

    @Column(nullable = false)
    private String playerId;

    @Column(nullable = false)
    private int minute;

    public abstract String getDescription();

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }
    public String getTeamId() { return teamId; }
    public void setTeamId(String teamId) { this.teamId = teamId; }
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}
