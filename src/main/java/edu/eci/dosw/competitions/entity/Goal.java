package edu.eci.dosw.competitions.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "goals")
public class Goal extends MatchEvent {

    private UUID assistPlayerId;

    private boolean isOwnGoal;

    @Override
    public String getDescription() {
        return isOwnGoal ? "Own goal at minute " + getMinute()
                : "Goal at minute " + getMinute() +
                (assistPlayerId != null ? " assisted by " + assistPlayerId : "");
    }

    // Getters y Setters
    public UUID getAssistPlayerId() { return assistPlayerId; }
    public void setAssistPlayerId(UUID assistPlayerId) { this.assistPlayerId = assistPlayerId; }
    public boolean isOwnGoal() { return isOwnGoal; }
    public void setOwnGoal(boolean ownGoal) { isOwnGoal = ownGoal; }
}