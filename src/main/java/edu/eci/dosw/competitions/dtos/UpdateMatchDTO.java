package edu.eci.dosw.competitions.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateMatchDTO {

    private UUID refereeId;
    private UUID fieldId;
    private LocalDateTime scheduledAt;

    public UUID getRefereeId() { return refereeId; }
    public void setRefereeId(UUID refereeId) { this.refereeId = refereeId; }

    public UUID getFieldId() { return fieldId; }
    public void setFieldId(UUID fieldId) { this.fieldId = fieldId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
}