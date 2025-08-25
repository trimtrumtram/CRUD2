package com.crudapi.crud.controller;

import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.enums.entityEnums.Role;
import com.crudapi.crud.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql("/db/migration/V1__create_tables.sql")
@Transactional
public class EmployeeControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    private CreateEmployeeDTO createDTO;
    private UpdateEmployeeDTO updateDTO;

    static {
        postgres
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        postgres.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void cleanUp() {
        employeeRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        createDTO = new CreateEmployeeDTO();
        createDTO.setEmail("test@mail.ru");
        createDTO.setFirstName("John");
        createDTO.setLastName("Created");
        createDTO.setPassword("1234");
        createDTO.setRole(Role.valueOf("MANAGER"));

        updateDTO = new UpdateEmployeeDTO();
        updateDTO.setEmail("updated@mail.ru");
        updateDTO.setFirstName("John");
        updateDTO.setLastName("Updated");
    }

    @Test
    void createEmployee_shouldReturnCreatedEmployee() throws Exception {
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value("test@mail.ru"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Created"));

    }

    @Test
    void createEmployee_shouldThrowException_whenEmailExists() throws Exception {
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(IllegalArgumentException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(
                        "Email already exists",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }


    @Test
    void updateEmployee() throws Exception{
        String response = mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andReturn().getResponse().getContentAsString();

        EmployeeResponseDTO created = objectMapper.readValue(response, EmployeeResponseDTO.class);

        mockMvc.perform(put("/employee/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value("updated@mail.ru"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Updated"));
    }

    @Test
    void deleteEmployee_shouldRemoveEmployee() throws Exception {
        String response = mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andReturn().getResponse().getContentAsString();

        EmployeeResponseDTO created = objectMapper.readValue(response, EmployeeResponseDTO.class);

        mockMvc.perform(delete("/employee/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        assertFalse(employeeRepository.existsById(created.getId()));
    }

    @Test
    void getAllEmployees_shouldReturnFilteredEmployees() throws Exception {
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee")
                        .param("roleFilter", "MANAGER")
                        .param("emailFilter", "test@mail.ru")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "LASTNAME")
                        .param("sortDirection", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("test@mail.ru"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}
