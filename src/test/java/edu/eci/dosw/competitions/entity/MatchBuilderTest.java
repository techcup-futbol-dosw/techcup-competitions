package edu.eci.dosw.competitions.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class MatchBuilderTest {

    private MatchBuilder matchBuilder;
    private UUID tournamentId;
    private UUID homeTeamId;
    private UUID awayTeamId;
    private UUID refereeId;
    private UUID fieldId;
    private LocalDateTime scheduledAt;

    @BeforeEach
    void setUp() {
        matchBuilder = new MatchBuilder();
        tournamentId = UUID.randomUUID();
        homeTeamId = UUID.randomUUID();
        awayTeamId = UUID.randomUUID();
        refereeId = UUID.randomUUID();
        fieldId = UUID.randomUUID();
        scheduledAt = LocalDateTime.now();
    }

    // ===== UNIT TESTS =====

    @Test
    void testWithTournament() {
        matchBuilder.withTournament(tournamentId);
        assertEquals(tournamentId, matchBuilder.getTournamentId());
    }

    @Test
    void testWithTeams() {
        matchBuilder.withTeams(homeTeamId, awayTeamId);
        assertEquals(homeTeamId, matchBuilder.getHomeTeamId());
        assertEquals(awayTeamId, matchBuilder.getAwayTeamId());
    }

    @Test
    void testWithReferee() {
        matchBuilder.withReferee(refereeId);
        assertEquals(refereeId, matchBuilder.getRefereeId());
    }

    @Test
    void testWithField() {
        matchBuilder.withField(fieldId);
        assertEquals(fieldId, matchBuilder.getFieldId());
    }

    @Test
    void testWithDate() {
        matchBuilder.withDate(scheduledAt);
        assertEquals(scheduledAt, matchBuilder.getScheduledAt());
    }

    @Test
    void testWithPhase() {
        matchBuilder.withPhase(MatchPhase.FINAL);
        assertEquals(MatchPhase.FINAL, matchBuilder.getPhase());
    }

    @Test
    void testBuildCreatesMatchWithScheduledStatus() {
        Match match = matchBuilder
                .withTournament(tournamentId)
                .withTeams(homeTeamId, awayTeamId)
                .build();
        assertEquals(MatchStatus.SCHEDULED, match.getStatus());
    }

    @Test
    void testBuildCreatesMatchWithCorrectTournament() {
        Match match = matchBuilder
                .withTournament(tournamentId)
                .build();
        assertEquals(tournamentId, match.getTournamentId());
    }

    @Test
    void testBuildCreatesMatchWithCorrectTeams() {
        Match match = matchBuilder
                .withTeams(homeTeamId, awayTeamId)
                .build();
        assertEquals(homeTeamId, match.getHomeTeamId());
        assertEquals(awayTeamId, match.getAwayTeamId());
    }

    // ===== INTEGRATION TESTS =====

    @Test
    void testFullBuildIntegration() {
        Match match = matchBuilder
                .withTournament(tournamentId)
                .withTeams(homeTeamId, awayTeamId)
                .withReferee(refereeId)
                .withField(fieldId)
                .withDate(scheduledAt)
                .withPhase(MatchPhase.GROUP_STAGE)
                .build();

        assertEquals(tournamentId, match.getTournamentId());
        assertEquals(homeTeamId, match.getHomeTeamId());
        assertEquals(awayTeamId, match.getAwayTeamId());
        assertEquals(refereeId, match.getRefereeId());
        assertEquals(fieldId, match.getFieldId());
        assertEquals(scheduledAt, match.getScheduledAt());
        assertEquals(MatchPhase.GROUP_STAGE, match.getPhase());
        assertEquals(MatchStatus.SCHEDULED, match.getStatus());
    }

    @Test
    void testBuiltMatchCanBeModifiedIntegration() {
        Match match = matchBuilder
                .withTournament(tournamentId)
                .withTeams(homeTeamId, awayTeamId)
                .withPhase(MatchPhase.SEMIFINALS)
                .build();

        assertTrue(match.canBeModified());
        assertTrue(match.canBeDeleted());

        match.finish();
        assertFalse(match.canBeModified());
        assertFalse(match.canBeDeleted());
    }

    @Test
    void testBuiltMatchWithStandingsIntegration() {
        Match match = matchBuilder
                .withTournament(tournamentId)
                .withTeams(homeTeamId, awayTeamId)
                .withPhase(MatchPhase.GROUP_STAGE)
                .build();

        Standings standings = new Standings();
        standings.setTournamentId(match.getTournamentId());
        standings.setTeamId(match.getHomeTeamId());

        match.setHomeScore(2);
        match.setAwayScore(1);
        standings.updateAfterMatch(match.getHomeScore(), match.getAwayScore());

        match.finish();

        assertEquals(MatchStatus.FINISHED, match.getStatus());
        assertEquals(1, standings.getMatchesWon());
        assertEquals(3, standings.getPoints());
    }
}