package edu.eci.dosw.competitions.dtos;

public class RegisterGoalDTO {

    private String matchId;
    private String teamId;
    private String playerId;
    private String assistPlayerId;
    private boolean isOwnGoal;
    private int minute;

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getTeamId() { return teamId; }
    public void setTeamId(String teamId) { this.teamId = teamId; }

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public String getAssistPlayerId() { return assistPlayerId; }
    public void setAssistPlayerId(String assistPlayerId) { this.assistPlayerId = assistPlayerId; }

    public boolean isOwnGoal() { return isOwnGoal; }
    public void setOwnGoal(boolean ownGoal) { isOwnGoal = ownGoal; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}
