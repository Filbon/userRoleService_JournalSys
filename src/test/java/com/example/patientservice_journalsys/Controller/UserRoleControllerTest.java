package com.example.patientservice_journalsys.Controller;

import com.example.patientservice_journalsys.DTO.ConditionDTO;
import com.example.patientservice_journalsys.DTO.EncounterDTO;
import com.example.patientservice_journalsys.DTO.PatientDTO;
import com.example.patientservice_journalsys.Model.ConditionStatus;
import com.example.patientservice_journalsys.Service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(userRoleController.class)
class UserRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRoleService userRoleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPatients_withValidRole() throws Exception {
        List<PatientDTO> patients = List.of(
                new PatientDTO(1L, "John Doe", LocalDate.of(2000, 1, 1), 101L, Collections.emptyList()),
                new PatientDTO(2L, "Jane Smith", LocalDate.of(1995, 5, 5), 102L, Collections.emptyList())
        );

        Mockito.when(userRoleService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/userRole/patients/GetPatients")
                        .header("userRole", "DOCTOR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    void testAddCondition_withValidRequest() throws Exception {
        ConditionDTO conditionDTO = new ConditionDTO(
                1L, "Condition A", "Description A", LocalDate.of(2024, 1, 1), 1L, ConditionStatus.ACTIVE
        );

        Mockito.when(userRoleService.addCondition(anyLong(), any(ConditionDTO.class))).thenReturn(conditionDTO);

        mockMvc.perform(post("/api/userRole/patients/1/conditions")
                        .header("userRole", "DOCTOR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conditionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.conditionName").value("Condition A")) // Updated to match the field name
                .andExpect(jsonPath("$.description").value("Description A"))
                .andExpect(jsonPath("$.diagnosisDate").value("2024-01-01"));
    }


    @Test
    void testAddEncounter_withValidRequest() throws Exception {
        EncounterDTO encounterDTO = new EncounterDTO(
                1L, LocalDateTime.of(2024, 1, 1, 10, 0), 2L, "Dr. Smith",
                3L, 4L, "Routine Checkup", "Good Outcome"
        );

        Mockito.when(userRoleService.addEncounter(anyLong(), anyLong(), any(EncounterDTO.class))).thenReturn(encounterDTO);

        mockMvc.perform(post("/api/userRole/practitioner/1/encounters")
                        .header("userRole", "DOCTOR")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(encounterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reason").value("Routine Checkup"))
                .andExpect(jsonPath("$.outcome").value("Good Outcome"))
                .andExpect(jsonPath("$.practitionerName").value("Dr. Smith"));
    }
}