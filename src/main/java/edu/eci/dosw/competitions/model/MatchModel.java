package edu.eci.dosw.competitions.model;

import edu.eci.dosw.competitions.entity.Match;
import edu.eci.dosw.competitions.entity.MatchStatus;

public class MatchModel {

    private Match match;
    private MatchState state;

    public MatchModel(Match match) {
        this.match = match;
        this.state = MatchStateFactory.toState(match.getStatus(), this);
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public Match getMatch() {
        return match;
    }

    public MatchStatus getStatus() {
        return match.getStatus();
    }

    public void start() {
        state.start();
        match.start();
    }

    public void finish() {
        state.finish();
        match.finish();
    }

    public void cancel() {
        state.cancel();
        match.cancel();
    }

    public void registerGoal() {
        state.registerGoal();
    }

    public void registerCard() {
        state.registerCard();
    }

    public void modify() {
        state.modify();
    }

    public void delete() {
        state.delete();
    }
}