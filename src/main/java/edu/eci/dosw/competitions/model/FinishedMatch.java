package edu.eci.dosw.competitions.model;

public class FinishedMatch extends MatchState {

    public FinishedMatch(MatchModel match) {
        super(match);
    }

    @Override
    public void start() {
        throw new RuntimeException("Cannot start a match that is already finished");
    }

    @Override
    public void finish() {
        throw new RuntimeException("Match is already finished");
    }

    @Override
    public void cancel() {
        throw new RuntimeException("Cannot cancel a match that is already finished");
    }

    @Override
    public void registerGoal() {
        throw new RuntimeException("Cannot register a goal in a finished match");
    }

    @Override
    public void registerCard() {
        throw new RuntimeException("Cannot register a card in a finished match");
    }

    @Override
    public void modify() {
        throw new RuntimeException("Cannot modify a finished match");
    }

    @Override
    public void delete() {
        throw new RuntimeException("Cannot delete a finished match");
    }
}