package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    private String id;

    @Column(nullable = false)
    private String tournamentId;

    @Column(nullable = false)
    private String homeTeamId;

    @Column(nullable = false)
    private String awayTeamId;

    private String refereeId;

    private String fieldId;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchPhase phase;

    private int homeScore;

    private int awayScore;

    public void start() {
        this.status = MatchStatus.IN_PROGRESS;
    }

    public void finish() {
        this.status = MatchStatus.FINISHED;
    }

    public void cancel() {
        this.status = MatchStatus.CANCELLED;
    }

    public boolean canBeModified() {
        return this.status == MatchStatus.SCHEDULED
                || this.status == MatchStatus.IN_PROGRESS;
    }

    public boolean canBeDeleted() {
        return this.status == MatchStatus.SCHEDULED;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTournamentId() { return tournamentId; }
    public void setTournamentId(String tournamentId) { this.tournamentId = tournamentId; }
    public String getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(String homeTeamId) { this.homeTeamId = homeTeamId; }
    public String getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(String awayTeamId) { this.awayTeamId = awayTeamId; }
    public String getRefereeId() { return refereeId; }
    public void setRefereeId(String refereeId) { this.refereeId = refereeId; }
    public String getFieldId() { return fieldId; }
    public void setFieldId(String fieldId) { this.fieldId = fieldId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public MatchStatus getStatus() { return status; }
    public void setStatus(MatchStatus status) { this.status = status; }
    public MatchPhase getPhase() { return phase; }
    public void setPhase(MatchPhase phase) { this.phase = phase; }
    public int getHomeScore() { return homeScore; }
    public void setHomeScore(int homeScore) { this.homeScore = homeScore; }
    public int getAwayScore() { return awayScore; }
    public void setAwayScore(int awayScore) { this.awayScore = awayScore; }
}