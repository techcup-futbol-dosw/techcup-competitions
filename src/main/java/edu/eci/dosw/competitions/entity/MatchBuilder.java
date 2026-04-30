package edu.eci.dosw.competitions.entity;

import java.time.LocalDateTime;

public class MatchBuilder {

    private String tournamentId;
    private String homeTeamId;
    private String awayTeamId;
    private String refereeId;
    private String fieldId;
    private LocalDateTime scheduledAt;
    private MatchPhase phase;

    public MatchBuilder withTournament(String tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public MatchBuilder withTeams(String homeId, String awayId) {
        this.homeTeamId = homeId;
        this.awayTeamId = awayId;
        return this;
    }

    public MatchBuilder withReferee(String refereeId) {
        this.refereeId = refereeId;
        return this;
    }

    public MatchBuilder withField(String fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public MatchBuilder withDate(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
        return this;
    }

    public MatchBuilder withPhase(MatchPhase phase) {
        this.phase = phase;
        return this;
    }

    public Match build() {
        Match match = new Match();
        match.setTournamentId(tournamentId);
        match.setHomeTeamId(homeTeamId);
        match.setAwayTeamId(awayTeamId);
        match.setRefereeId(refereeId);
        match.setFieldId(fieldId);
        match.setScheduledAt(scheduledAt);
        match.setPhase(phase);
        match.setStatus(MatchStatus.SCHEDULED);
        return match;
    }

    // Getters
    public String getTournamentId() { return tournamentId; }
    public String getHomeTeamId() { return homeTeamId; }
    public String getAwayTeamId() { return awayTeamId; }
    public String getRefereeId() { return refereeId; }
    public String getFieldId() { return fieldId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public MatchPhase getPhase() { return phase; }
}