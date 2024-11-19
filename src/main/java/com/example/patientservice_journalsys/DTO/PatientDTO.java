package com.example.patientservice_journalsys.DTO;

import java.time.LocalDate;
import java.util.List;

public class PatientDTO {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private Long userId; // Instead of embedding the entire User object
    private List<ConditionDTO> conditions; // List of Condition IDs instead of ConditionDTO objects

    public PatientDTO(Long id, String name, LocalDate birthdate, Long userId, List<ConditionDTO> conditions) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.userId = userId;
        this.conditions = conditions;
    }

    public PatientDTO() {
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ConditionDTO> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionDTO> conditions) {
        this.conditions = conditions;
    }
}

