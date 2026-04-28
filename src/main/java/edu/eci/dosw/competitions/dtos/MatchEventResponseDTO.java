package edu.eci.dosw.competitions.dtos;

import java.util.UUID;

public class MatchEventResponseDTO {

    private UUID id;
    private UUID matchId;
    private UUID teamId;
    private UUID playerId;
    private int minute;
    private String description;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getMatchId() { return matchId; }
    public void setMatchId(UUID matchId) { this.matchId = matchId; }

    public UUID getTeamId() { return teamId; }
    public void setTeamId(UUID teamId) { this.teamId = teamId; }

    public UUID getPlayerId() { return playerId; }
    public void setPlayerId(UUID playerId) { this.playerId = playerId; }

    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}