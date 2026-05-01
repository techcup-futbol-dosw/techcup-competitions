package edu.eci.dosw.competitions.model;

public abstract class MatchState {

    protected MatchModel match;

    public MatchState(MatchModel match) {
        this.match = match;
    }

    public abstract void start();
    public abstract void finish();
    public abstract void cancel();
    public abstract void registerGoal();
    public abstract void registerCard();
    public abstract void modify();
    public abstract void delete();
}