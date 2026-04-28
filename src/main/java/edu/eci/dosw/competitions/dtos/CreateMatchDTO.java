package edu.eci.dosw.competitions.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateMatchDTO {

    private UUID tournamentId;
    private UUID homeTeamId;
    private UUID awayTeamId;
    private UUID refereeId;
    private UUID fieldId;
    private LocalDateTime scheduledAt;
    private String phase;

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

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
}