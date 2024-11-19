package com.example.patientservice_journalsys.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient_condition")  // Renamed to avoid reserved word
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "condition_name")  // Optional: if you want custom column names
    private String name;

    private String description;

    private LocalDate diagnosisDate;

    @Enumerated(EnumType.STRING)
    private ConditionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)  // Lazy loading for performance
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Default constructor
    public Condition() {
    }

    // Constructor with all fields
    public Condition(Long id, String name, String description, LocalDate diagnosisDate, ConditionStatus status, Patient patient) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.diagnosisDate = diagnosisDate;
        this.status = status;
        this.patient = patient;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public ConditionStatus getStatus() {
        return status;
    }

    public void setStatus(ConditionStatus status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

