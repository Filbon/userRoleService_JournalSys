package com.example.patientservice_journalsys.Controller;

import com.example.patientservice_journalsys.DTO.ConditionDTO;
import com.example.patientservice_journalsys.DTO.PatientDTO;
import com.example.patientservice_journalsys.DTO.PractitionerDTO;
import com.example.patientservice_journalsys.Service.UserRoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/userRole")
public class userRoleController {

    private final UserRoleService userRoleService;

    public userRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @GetMapping("/patients/GetPatients")
    public ResponseEntity<List<PatientDTO>> getAllPatients(@RequestHeader("userRole") String role) {
        if (Objects.equals(role, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }

        List<PatientDTO> patients = userRoleService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    @GetMapping("/myPage")
    public ResponseEntity<PatientDTO> getPatientById(@RequestHeader("patientId") Long id) {
        try {
            PatientDTO patientDTO = userRoleService.getPatientDetails(id);
            return ResponseEntity.ok(patientDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/patients/{patientId}/conditions")
    public ResponseEntity<ConditionDTO> addCondition(@RequestHeader("userRole") String role, @PathVariable Long patientId,
                                                     @RequestBody ConditionDTO conditionDTO) {
        System.out.println(role);
        if (Objects.equals(role, "PATIENT")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }
        try {
            ConditionDTO createdCondition = userRoleService.addCondition(patientId,conditionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCondition);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/patients/{id}/details")
    public ResponseEntity<PatientDTO> getPatientDetails(@RequestHeader("userRole") String role, @PathVariable Long id) {
        if (!Objects.equals(role, "DOCTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Return 403 if not authorized
        }
        try {
            PatientDTO patientDTO = userRoleService.getPatientDetails(id);
            return ResponseEntity.ok(patientDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/patient/create")
    public void createPatient(@RequestBody PatientDTO patientDTO){
        userRoleService.createPatient(patientDTO);
    }
    @PostMapping("/practitioner/create")
    public void createPractitioner(@RequestBody PractitionerDTO practitionerDTO){
        userRoleService.createPractitioner(practitionerDTO);
    }

    @GetMapping("patientId/byUserId/{id}")
    public ResponseEntity<?> getPatientIdByUserId(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid user ID provided.");
            }
            Long patientId = userRoleService.getPatientIdByUserId(id);
            if (patientId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient ID not found for user ID: " + id);
            }
            return ResponseEntity.ok(patientId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching patient ID: " + e.getMessage());
        }
    }

}
