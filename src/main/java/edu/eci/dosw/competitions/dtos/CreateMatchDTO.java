package edu.eci.dosw.competitions.dtos;

import java.time.LocalDateTime;

public class CreateMatchDTO {

    private String tournamentId;
    private String homeTeamId;
    private String awayTeamId;
    private String refereeId;
    private String fieldId;
    private LocalDateTime scheduledAt;
    private String phase;

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

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
}