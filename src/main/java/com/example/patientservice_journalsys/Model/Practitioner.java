package com.example.patientservice_journalsys.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Practitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String specialization;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "practitioner")
    private List<Encounter> encounters;

    private Long userId;

    public Practitioner(Long id, String name, String specialization, Role role, List<Encounter> encounters, Long userId) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.role = role;
        this.encounters = encounters;
        this.userId = userId;
    }

    public Practitioner() {
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

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
