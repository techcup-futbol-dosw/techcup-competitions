package edu.eci.dosw.competitions.model;

public class InProgressMatch extends MatchState {

    public InProgressMatch(MatchModel match) {
        super(match);
    }

    @Override
    public void start() {
        throw new RuntimeException("Match is already in progress");
    }

    @Override
    public void finish() {
        match.setState(new FinishedMatch(match));
    }

    @Override
    public void cancel() {
        throw new RuntimeException("Cannot cancel a match that is in progress");
    }

    @Override
    public void registerGoal() {
        // Permitido, no hace nada especial
    }

    @Override
    public void registerCard() {
        // Permitido, no hace nada especial
    }

    @Override
    public void modify() {
        // Permitido en progreso, no hace nada especial
    }

    @Override
    public void delete() {
        throw new RuntimeException("Cannot delete a match that is in progress");
    }
}