package com.example.patientservice_journalsys.Repository;

import com.example.patientservice_journalsys.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}