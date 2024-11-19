package com.example.patientservice_journalsys.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthdate;

    private Long userId; // Storing userId instead of the whole User object

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Condition> conditions;

    public Patient(Long id, String name, LocalDate birthdate, Long userId, List<Condition> conditions) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.userId = userId;
        this.conditions = conditions;
    }

    public Patient() {

    }

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

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}

