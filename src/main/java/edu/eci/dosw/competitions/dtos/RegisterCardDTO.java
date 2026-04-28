package edu.eci.dosw.competitions.dtos;

import java.util.UUID;

public class RegisterCardDTO {

    private UUID matchId;
    private UUID teamId;
    private UUID playerId;
    private String cardType;
    private int minute;

    public UUID getMatchId() { return matchId; }
    public void setMatchId(UUID matchId) { this.matchId = matchId; }

    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }

    public UUID getPlayerId() { return playerId; }
    public void setPlayerId(UUID playerId) { this.playerId = playerId; }

    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
}