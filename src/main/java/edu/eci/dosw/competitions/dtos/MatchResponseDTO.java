package edu.eci.dosw.competitions.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchResponseDTO {

    private UUID id;
    private UUID tournamentId;
    private UUID homeTeamId;
    private UUID awayTeamId;
    private UUID refereeId;
    private UUID fieldId;
    private LocalDateTime scheduledAt;
    private String status;
    private String phase;
    private int homeScore;
    private int awayScore;

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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public int getHomeScore() { return homeScore; }
    public void setHomeScore(int homeScore) { this.homeScore = homeScore; }

    public int getAwayScore() { return awayScore; }
    public void setAwayScore(int awayScore) { this.awayScore = awayScore; }
}