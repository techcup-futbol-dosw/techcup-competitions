package edu.eci.dosw.competitions.model;

import edu.eci.dosw.competitions.entity.Lineup;

public class LineupModel {

    private Lineup lineup;
    private LineupState state;

    public LineupModel(Lineup lineup) {
        this.lineup = lineup;
        this.state = lineup.isConfirmed()
                ? new ConfirmedLineup(this)
                : new UnconfirmedLineup(this);
    }

    public void setState(LineupState state) {
        this.state = state;
    }

    public Lineup getLineup() {
        return lineup;
    }

    public boolean isConfirmed() {
        return lineup.isConfirmed();
    }

    public void update() {
        state.update();
    }

    public void confirm() {
        state.confirm();
        lineup.confirm();
    }
}