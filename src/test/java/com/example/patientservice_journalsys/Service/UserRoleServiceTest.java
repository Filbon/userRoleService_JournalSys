package com.example.patientservice_journalsys.Service;

import com.example.patientservice_journalsys.DTO.ConditionDTO;
import com.example.patientservice_journalsys.DTO.EncounterDTO;
import com.example.patientservice_journalsys.DTO.PatientDTO;
import com.example.patientservice_journalsys.DTO.UserDTO;
import com.example.patientservice_journalsys.Model.Condition;
import com.example.patientservice_journalsys.Model.Encounter;
import com.example.patientservice_journalsys.Model.Patient;
import com.example.patientservice_journalsys.Model.Practitioner;
import com.example.patientservice_journalsys.Repository.ConditionRepository;
import com.example.patientservice_journalsys.Repository.EncounterRepository;
import com.example.patientservice_journalsys.Repository.PatientRepository;
import com.example.patientservice_journalsys.Repository.PractitionerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ConditionRepository conditionRepository;

    @Mock
    private PractitionerRepository practitionerRepository;

    @Mock
    private EncounterRepository encounterRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserRoleService userRoleService;

    @Spy
    private ModelMapper modelMapper;

    private static final String USER_SERVICE_URL = "https://userservice.app.cloud.cbh.kth.se/api/user";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatients_Success() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
        patient.setBirthdate(LocalDate.of(1990, 1, 1));
        patient.setUserId(100L);
        patient.setConditions(new ArrayList<>());

        when(patientRepository.findAll()).thenReturn(List.of(patient));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(100L);
        userDTO.setUserName("johndoe");

        when(restTemplate.exchange(
                eq(USER_SERVICE_URL + "/users/{id}"),
                eq(HttpMethod.GET),
                isNull(),
                eq(UserDTO.class),
                eq(100L)
        )).thenReturn(new ResponseEntity<>(userDTO, HttpStatus.OK));

        // Act
        List<PatientDTO> result = userRoleService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals(100L, result.get(0).getUserId());
        verify(patientRepository, times(1)).findAll();
        verify(restTemplate, times(1)).exchange(anyString(), any(), any(), eq(UserDTO.class), anyLong());
    }

    @Test
    void testGetPatientById_PatientNotFound() {
        // Arrange
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userRoleService.getPatientById(1L));
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testAddCondition_Success() {
        // Arrange
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");

        ConditionDTO conditionDTO = new ConditionDTO();
        conditionDTO.setDescription("Diabetes");

        Condition condition = new Condition();
        condition.setDescription("Diabetes");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(conditionRepository.save(any(Condition.class))).thenReturn(condition);

        // Act
        ConditionDTO result = userRoleService.addCondition(1L, conditionDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Diabetes", result.getDescription());
        verify(patientRepository, times(1)).findById(1L);
        verify(conditionRepository, times(1)).save(any(Condition.class));
    }

    @Test
    void testDeletePatient_Success() {
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(true);

        // Act
        userRoleService.deletePatient(1L);

        // Assert
        verify(patientRepository, times(1)).existsById(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePatient_NotFound() {
        // Arrange
        when(patientRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userRoleService.deletePatient(1L));
        verify(patientRepository, times(1)).existsById(1L);
    }

    @Test
    void testAddEncounter_Success() {
        // Arrange
        Practitioner practitioner = new Practitioner();
        practitioner.setId(1L);
        practitioner.setName("Dr. Smith");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");

        EncounterDTO encounterDTO = new EncounterDTO();
        encounterDTO.setReason("Check-up");

        Encounter encounter = new Encounter();
        encounter.setReason("Check-up");

        when(practitionerRepository.findById(1L)).thenReturn(Optional.of(practitioner));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(encounterRepository.save(any(Encounter.class))).thenReturn(encounter);

        // Act
        EncounterDTO result = userRoleService.addEncounter(1L, 1L, encounterDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Check-up", result.getReason());
        verify(practitionerRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).findById(1L);
        verify(encounterRepository, times(1)).save(any(Encounter.class));
    }
}
