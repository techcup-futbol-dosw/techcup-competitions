package edu.eci.dosw.competitions.dtos;

public class RegisterCardDTO {

    private String matchId;
    private String teamId;
    private String playerId;
    private String cardType;
    private int minute;

    public String getMatchId() { return matchId; }
    public void setMatchId(String matchId) { this.matchId = matchId; }

    public String getTeamId() { return teamId; }
    public void setTeamId(String teamId) { this.teamId = teamId; }

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}