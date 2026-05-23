package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "referee_assignments")
public class RefereeAssignment {

    @Id
    private String id;

    @Column(nullable = false)
    private String matchId;

    @Column(nullable = false)
    private Long refereeId;  // accountId del árbitro

    @Column(nullable = false)
    private boolean active;

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }
    public Long getRefereeId() { return refereeId; }
    public void setRefereeId(Long refereeId) { this.refereeId = refereeId; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}