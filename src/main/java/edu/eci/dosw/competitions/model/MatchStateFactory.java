package edu.eci.dosw.competitions.model;

import edu.eci.dosw.competitions.entity.MatchStatus;

public class MatchStateFactory {

    public static MatchState toState(MatchStatus status, MatchModel match) {
        return switch (status) {
            case SCHEDULED -> new ScheduledMatch(match);
            case IN_PROGRESS -> new InProgressMatch(match);
            case FINISHED -> new FinishedMatch(match);
            case CANCELLED -> new CancelledMatch(match);
        };
    }
}