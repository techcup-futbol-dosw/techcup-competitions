package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_audits")
public class MatchAudit {

    @Id
    private String id;

    @Column(nullable = false)
    private String matchId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchAuditAction action;

    private String detail;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public String getDescription() {
        return "Action: " + action + " | Detail: " + detail + " | At: " + timestamp;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }
    public MatchAuditAction getAction() { return action; }
    public void setAction(MatchAuditAction action) { this.action = action; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}