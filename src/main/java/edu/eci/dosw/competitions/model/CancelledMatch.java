package edu.eci.dosw.competitions.model;

public class CancelledMatch extends MatchState {

    public CancelledMatch(MatchModel match) {
        super(match);
    }

    @Override
    public void start() {
        throw new RuntimeException("Cannot start a cancelled match");
    }

    @Override
    public void finish() {
        throw new RuntimeException("Cannot finish a cancelled match");
    }

    @Override
    public void cancel() {
        throw new RuntimeException("Match is already cancelled");
    }

    @Override
    public void registerGoal() {
        throw new RuntimeException("Cannot register a goal in a cancelled match");
    }

    @Override
    public void registerCard() {
        throw new RuntimeException("Cannot register a card in a cancelled match");
    }

    @Override
    public void modify() {
        throw new RuntimeException("Cannot modify a cancelled match");
    }

    @Override
    public void delete() {
        throw new RuntimeException("Cannot delete a cancelled match");
    }
}