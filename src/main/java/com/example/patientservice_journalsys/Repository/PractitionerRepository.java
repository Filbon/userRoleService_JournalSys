package com.example.patientservice_journalsys.Repository;

import com.example.patientservice_journalsys.Model.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {

    Practitioner findByUserId(Long id);
}
