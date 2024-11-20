package com.example.patientservice_journalsys.DTO;

import java.time.LocalDateTime;

public class ObservationDTO {
    private Long id;
    private String description; // Renamed to match the entity's 'description' field
    private String value;
    private Long encounterId;
    private Long practitionerId; // Added to match 'practitioner' in entity
    private LocalDateTime observationTime; // Added to match 'observationTime' in entity

    public ObservationDTO(Long id, String description, String value, Long encounterId, Long practitionerId, LocalDateTime observationTime) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.encounterId = encounterId;
        this.practitionerId = practitionerId;
        this.observationTime = observationTime;
    }
    public ObservationDTO(){

    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getPractitionerId() {
        return practitionerId;
    }

    public void setPractitionerId(Long practitionerId) {
        this.practitionerId = practitionerId;
    }

    public LocalDateTime getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(LocalDateTime observationTime) {
        this.observationTime = observationTime;
    }
}
