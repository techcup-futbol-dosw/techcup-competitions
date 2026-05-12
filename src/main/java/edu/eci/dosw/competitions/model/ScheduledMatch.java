package edu.eci.dosw.competitions.model;

public class ScheduledMatch extends MatchState {

    public ScheduledMatch(MatchModel match) {
        super(match);
    }

    @Override
    public void start() {
        match.setState(new InProgressMatch(match));
    }

    @Override
    public void finish() {
        throw new RuntimeException("Cannot finish a match that has not started");
    }

    @Override
    public void cancel() {
        match.setState(new CancelledMatch(match));
    }

    @Override
    public void registerGoal() {
        throw new RuntimeException("Cannot register a goal in a scheduled match");
    }

    @Override
    public void registerCard() {
        throw new RuntimeException("Cannot register a card in a scheduled match");
    }

    @Override
    public void modify() {
        // Permitido, no hace nada especial
    }

    @Override
    public void delete() {
        // Permitido, no hace nada especial
    }
}