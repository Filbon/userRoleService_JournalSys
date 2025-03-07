package com.example.patientservice_journalsys.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.patientservice_journalsys.Model.Condition;

import java.util.List;

public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findByPatientId(Long id);
}
