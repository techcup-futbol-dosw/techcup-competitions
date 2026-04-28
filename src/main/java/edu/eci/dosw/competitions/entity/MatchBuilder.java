package edu.eci.dosw.competitions.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class MatchBuilder {

    private UUID tournamentId;
    private UUID homeTeamId;
    private UUID awayTeamId;
    private UUID refereeId;
    private UUID fieldId;
    private LocalDateTime scheduledAt;
    private MatchPhase phase;

    public MatchBuilder withTournament(UUID tournamentId) {
        this.tournamentId = tournamentId;
        return this;
    }

    public MatchBuilder withTeams(UUID homeId, UUID awayId) {
        this.homeTeamId = homeId;
        this.awayTeamId = awayId;
        return this;
    }

    public MatchBuilder withReferee(UUID refereeId) {
        this.refereeId = refereeId;
        return this;
    }

    public MatchBuilder withField(UUID fieldId) {
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
    public UUID getTournamentId() { return tournamentId; }
    public UUID getHomeTeamId() { return homeTeamId; }
    public UUID getAwayTeamId() { return awayTeamId; }
    public UUID getRefereeId() { return refereeId; }
    public UUID getFieldId() { return fieldId; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public MatchPhase getPhase() { return phase; }
}