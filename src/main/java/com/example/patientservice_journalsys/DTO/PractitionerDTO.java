package com.example.patientservice_journalsys.DTO;

import com.example.patientservice_journalsys.Model.Role;

import java.util.List;

public class PractitionerDTO {

    private Long id;
    private String name;
    private String specialization;
    private Role role;
    private List<Long> encounterIds;  // Changed to a list of encounterIds (ids of the encounters)
    private Long userId;  // Added userId to expose the ID of the user

    // Constructor
    public PractitionerDTO(Long id, String name, String specialization, Role role, Long organizationId, List<Long> encounterIds, Long userId) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.role = role;
        this.encounterIds = encounterIds;
        this.userId = userId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Long> getEncounterIds() {
        return encounterIds;
    }

    public void setEncounterIds(List<Long> encounterIds) {
        this.encounterIds = encounterIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
