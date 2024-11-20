package com.example.patientservice_journalsys.Repository;

import com.example.patientservice_journalsys.Model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation,Long> {
}
