package com.example.patientservice_journalsys.Service;

import com.example.patientservice_journalsys.DTO.ConditionDTO;
import com.example.patientservice_journalsys.DTO.PatientDTO;
import com.example.patientservice_journalsys.DTO.UserDTO;
import com.example.patientservice_journalsys.Model.Condition;
import com.example.patientservice_journalsys.Model.Patient;
import com.example.patientservice_journalsys.Repository.ConditionRepository;
import com.example.patientservice_journalsys.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ConditionRepository conditionRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate; // For making API calls to User service

    private static final String USER_SERVICE_URL = "http://localhost:8080/api/user"; // Adjust the base URL as needed

    @Autowired
    public PatientService(PatientRepository patientRepository,
                          ConditionRepository conditionRepository,
                          ModelMapper modelMapper,
                          RestTemplate restTemplate) {
        this.patientRepository = patientRepository;
        this.conditionRepository = conditionRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
    }

    // Get all patients, now directly fetching conditions from the database
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientDTO> patientDTOs = new ArrayList<>();

        for (Patient patient : patients) {
            PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);

            // Fetch user details via the User service using the userId stored in the patient
            String userApiUrl = USER_SERVICE_URL + "/users/{id}";
            ResponseEntity<UserDTO> userResponse = restTemplate.exchange(userApiUrl, HttpMethod.GET, null, UserDTO.class, patient.getUserId());

            if (userResponse.getStatusCode() == HttpStatus.OK) {
                UserDTO userDTO = userResponse.getBody();
                assert userDTO != null;
                patientDTO.setUserId(userDTO.getId());
            } else {
                throw new EntityNotFoundException("User not found");
            }

            // Map conditions from the Patient entity (conditions are now directly stored in the Patient)
            List<ConditionDTO> conditionDTOs = patient.getConditions().stream()
                    .map(condition -> modelMapper.map(condition, ConditionDTO.class))
                    .collect(Collectors.toList());

            // Set conditions in the PatientDTO
            patientDTO.setConditions(conditionDTOs);

            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }

    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);

        // Fetch user details via the User service using the userId stored in the patient
        String userApiUrl = USER_SERVICE_URL + "/users/{id}";
        ResponseEntity<UserDTO> userResponse = restTemplate.exchange(userApiUrl, HttpMethod.GET, null, UserDTO.class, patient.getUserId());

        if (userResponse.getStatusCode() == HttpStatus.OK) {
            UserDTO userDTO = userResponse.getBody();
            assert userDTO != null;
            patientDTO.setUserId(userDTO.getId());
        } else {
            throw new EntityNotFoundException("User not found");
        }

        // Map conditions from the Patient entity (conditions are now directly stored in the Patient)
        List<ConditionDTO> conditionDTOs = patient.getConditions().stream()
                .map(condition -> modelMapper.map(condition, ConditionDTO.class))
                .collect(Collectors.toList());

        // Set conditions in the PatientDTO
        patientDTO.setConditions(conditionDTOs);

        return patientDTO;
    }

    // Create a new patient

    // Delete a patient by ID
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    // Method to add a condition (diagnosis) to a patient
    public ConditionDTO addCondition(Long patientId, ConditionDTO conditionDTO) {
        // Ensure the patient exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        // Create a new condition and link it to the patient
        Condition condition = modelMapper.map(conditionDTO, Condition.class);
        condition.setPatient(patient);

        // Save the condition and add it to the patient's conditions list
        Condition savedCondition = conditionRepository.save(condition);
        patient.getConditions().add(savedCondition); // Add the saved condition directly to the patient's list
        patientRepository.save(patient); // Save the updated patient with the new condition

        return modelMapper.map(savedCondition, ConditionDTO.class); // Return the saved condition as DTO
    }

    // Fetch patient details (with conditions, encounters, etc.)
    public PatientDTO getPatientDetails(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
        patientDTO.setUserId(patient.getUserId());

        // Map conditions from the Patient entity (conditions are now directly stored in the Patient)
        List<ConditionDTO> conditionDTOs = patient.getConditions().stream()
                .map(condition -> modelMapper.map(condition, ConditionDTO.class))
                .collect(Collectors.toList());

        // Set conditions in the PatientDTO
        patientDTO.setConditions(conditionDTOs);

        return patientDTO;
    }
}

