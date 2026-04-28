package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID tournamentId;

    @Column(nullable = false)
    private UUID homeTeamId;

    @Column(nullable = false)
    private UUID awayTeamId;

    private UUID refereeId;

    private UUID fieldId;

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
        this.status = MatchStatus.SCHEDULED;
    }

    public void finish() {
        this.status = MatchStatus.FINISHED;
    }

    public void cancel() {
        this.status = MatchStatus.CANCELLED;
    }

    public boolean canBeModified() {
        return this.status == MatchStatus.SCHEDULED;
    }

    public boolean canBeDeleted() {
        return this.status == MatchStatus.SCHEDULED;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getTournamentId() { return tournamentId; }
    public void setTournamentId(UUID tournamentId) { this.tournamentId = tournamentId; }
    public UUID getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(UUID homeTeamId) { this.homeTeamId = homeTeamId; }
    public UUID getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(UUID awayTeamId) { this.awayTeamId = awayTeamId; }
    public UUID getRefereeId() { return refereeId; }
    public void setRefereeId(UUID refereeId) { this.refereeId = refereeId; }
    public UUID getFieldId() { return fieldId; }
    public void setFieldId(UUID fieldId) { this.fieldId = fieldId; }
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