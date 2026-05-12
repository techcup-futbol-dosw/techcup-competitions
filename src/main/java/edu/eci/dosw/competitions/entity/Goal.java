package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "goals")
public class Goal extends MatchEvent {

    private String assistPlayerId;

    private boolean isOwnGoal;

    @Override
    public String getDescription() {
        return isOwnGoal ? "Own goal at minute " + getMinute()
                : "Goal at minute " + getMinute() +
                (assistPlayerId != null ? " assisted by " + assistPlayerId : "");
    }

    // Getters y Setters
    public String getAssistPlayerId() { return assistPlayerId; }
    public void setAssistPlayerId(String assistPlayerId) { this.assistPlayerId = assistPlayerId; }
    public boolean isOwnGoal() { return isOwnGoal; }
    public void setOwnGoal(boolean ownGoal) { isOwnGoal = ownGoal; }
}