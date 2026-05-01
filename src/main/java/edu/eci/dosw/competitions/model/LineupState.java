package edu.eci.dosw.competitions.model;

public abstract class LineupState {

    protected LineupModel lineup;

    public LineupState(LineupModel lineup) {
        this.lineup = lineup;
    }

    public abstract void update();
    public abstract void confirm();
}