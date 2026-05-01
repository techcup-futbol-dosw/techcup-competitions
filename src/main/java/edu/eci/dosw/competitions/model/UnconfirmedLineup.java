package edu.eci.dosw.competitions.model;

public class UnconfirmedLineup extends LineupState {

    public UnconfirmedLineup(LineupModel lineup) {
        super(lineup);
    }

    @Override
    public void update() {
        // Permitido, no hace nada especial
    }

    @Override
    public void confirm() {
        lineup.setState(new ConfirmedLineup(lineup));
    }
}