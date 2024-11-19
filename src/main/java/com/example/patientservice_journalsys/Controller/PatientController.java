package com.example.patientservice_journalsys.Controller;

import com.example.patientservice_journalsys.DTO.ConditionDTO;
import com.example.patientservice_journalsys.DTO.PatientDTO;
import com.example.patientservice_journalsys.Service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;



    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/GetPatients")
    public ResponseEntity<List<PatientDTO>> getAllPatients(@RequestHeader("userRole") String role) {
        if (Objects.equals(role, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }

        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    @GetMapping("/myPage")
    public ResponseEntity<PatientDTO> getPatientById(@RequestHeader("userId") Long id) {
        try {
            PatientDTO patientDTO = patientService.getPatientDetails(id);
            System.out.println(patientDTO);
            return ResponseEntity.ok(patientDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{patientId}/conditions")
    public ResponseEntity<ConditionDTO> addCondition(@RequestHeader("userRole") String role, @PathVariable Long patientId,
                                                     @RequestBody ConditionDTO conditionDTO) {
        System.out.println(role);
        if (Objects.equals(role, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }
        try {
            // Call service to add the condition
            ConditionDTO createdCondition = patientService.addCondition(patientId,conditionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCondition);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<PatientDTO> getPatientDetails(@RequestHeader("userRole") String role, @PathVariable Long id) {
        if (!Objects.equals(role, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }
        try {
            PatientDTO patientDTO = patientService.getPatientDetails(id);
            return ResponseEntity.ok(patientDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
