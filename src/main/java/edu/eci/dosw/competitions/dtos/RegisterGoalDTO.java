package edu.eci.dosw.competitions.dtos;

import java.util.UUID;

public class RegisterGoalDTO {

    private UUID matchId;
    private UUID teamId;
    private UUID playerId;
    private UUID assistPlayerId;
    private boolean isOwnGoal;
    private int minute;

    public UUID getMatchId() { return matchId; }
    public void setMatchId(UUID matchId) { this.matchId = matchId; }

    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }

    public UUID getPlayerId() { return playerId; }
    public void setPlayerId(UUID playerId) { this.playerId = playerId; }

    public UUID getAssistPlayerId() { return assistPlayerId; }
    public void setAssistPlayerId(UUID assistPlayerId) { this.assistPlayerId = assistPlayerId; }

    public boolean isOwnGoal() { return isOwnGoal; }
    public void setOwnGoal(boolean ownGoal) { isOwnGoal = ownGoal; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}
