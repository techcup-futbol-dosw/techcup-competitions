package edu.eci.dosw.competitions.model;

public class ConfirmedLineup extends LineupState {

    public ConfirmedLineup(LineupModel lineup) {
        super(lineup);
    }

    @Override
    public void update() {
        throw new RuntimeException("Cannot update a confirmed lineup");
    }

    @Override
    public void confirm() {
        throw new RuntimeException("Lineup is already confirmed");
    }
}