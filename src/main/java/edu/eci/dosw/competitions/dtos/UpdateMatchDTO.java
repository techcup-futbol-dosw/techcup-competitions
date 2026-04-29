package edu.eci.dosw.competitions.dtos;

import java.time.LocalDateTime;

public class UpdateMatchDTO {

    private String refereeId;
    private String fieldId;
    private LocalDateTime scheduledAt;

    public String getRefereeId() { return refereeId; }
    public void setRefereeId(String refereeId) { this.refereeId = refereeId; }

    public String getFieldId() { return fieldId; }
    public void setFieldId(String fieldId) { this.fieldId = fieldId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
}