package com.example.patientservice_journalsys.DTO;

import java.time.LocalDateTime;

public class EncounterDTO {
    private Long id;
    private LocalDateTime encounterDate;
    private Long patientId;
    private String practitionerName;
    private Long locationId;
    private Long practitionerId;
    private String reason;
    private String outcome;

    public EncounterDTO(Long id, LocalDateTime encounterDate, Long patientId, String practitionerName,
                        Long locationId, Long practitionerId, String reason, String outcome) {
        this.id = id;
        this.encounterDate = encounterDate;
        this.patientId = patientId;
        this.practitionerName = practitionerName;
        this.locationId = locationId;
        this.practitionerId = practitionerId;
        this.reason = reason;
        this.outcome = outcome;
    }

    public EncounterDTO() {
    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEncounterDate() {
        return encounterDate;
    }

    public void setEncounterDate(LocalDateTime encounterDate) {
        this.encounterDate = encounterDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPractitionerName() {
        return practitionerName;
    }

    public void setPractitionerName(String practitionerName) {
        this.practitionerName = practitionerName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
