package com.example.patientservice_journalsys.Repository;

import com.example.patientservice_journalsys.Model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EncounterRepository extends JpaRepository<Encounter,Long> {
}
