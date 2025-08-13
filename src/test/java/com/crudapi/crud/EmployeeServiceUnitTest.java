package com.crudapi.crud;

import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.mapper.entityMapper.EmployeeMapper;
import com.crudapi.crud.model.Employee;
import com.crudapi.crud.repository.EmployeeRepository;
import com.crudapi.crud.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EmployeeServiceUnitTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private Employee savedEmployee;
    private EmployeeResponseDTO responseDTO;
    private CreateEmployeeDTO createDTO;
    private UpdateEmployeeDTO updateDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        createDTO = new CreateEmployeeDTO();
        createDTO.setEmail("test@mail.ru");
        createDTO.setFirstName("John");
        createDTO.setLastName("Created");

        updateDTO = new UpdateEmployeeDTO();
        updateDTO.setEmail("updated@mail.ru");
        updateDTO.setFirstName("John");
        updateDTO.setLastName("Updated");

        employee = new Employee();
        employee.setId(1L);
        employee.setEmail("test@mail.ru");
        employee.setFirstName("John");
        employee.setLastName("Created");

        savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setEmail("test@mail.ru");
        savedEmployee.setFirstName("John");
        savedEmployee.setLastName("Created");

        responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setEmail("test@mail.ru");
        responseDTO.setFirstName("John");
        responseDTO.setLastName("Created");
    }

    @Test
    void createEmployee_shouldThrowException_whenEmailExist() {
        when(employeeRepository.existsEmployeeByEmail("test@mail.ru")).thenReturn(true);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.createEmployee(createDTO)
        );

        assertEquals("Email already exists", e.getMessage());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void createEmployee_shouldSaveAndReturnDto_whenEmailNotExist() {
        when(employeeRepository.existsEmployeeByEmail("test@mail.ru")).thenReturn(false);
        when(employeeMapper.mapToEntity(createDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(savedEmployee);
        when(employeeMapper.mapToDTO(employee)).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.createEmployee(createDTO);

        assertNotNull(result);

        assertEquals("test@mail.ru", result.getEmail());

        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployee_shouldUpdateAndReturnDTO() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsEmployeeByEmail("updated@mail.ru")).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapToDTO(employee)).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.updateEmployee(1L, updateDTO);

        assertNotNull(result);

        verify(employeeRepository).save(employee);

        verify(employeeMapper).mapToDTO(employee);
    }

    @Test
    void updateEmployee_shouldThrowException_whenEmailExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.existsEmployeeByEmail("updated@mail.ru")).thenReturn(true);

        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.updateEmployee(1L, updateDTO)
        );

        assertEquals("Employee with email updated@mail.ru already exists", e.getMessage());
    }

    @Test
    void deleteEmployee() {
        employeeService.deleteEmployee(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void getAllEmployees_shouldReturnPageOfDTOs() {
        EmployeeFilterDTO filter = new EmployeeFilterDTO();
        filter.setPage(0);
        filter.setSize(10);

        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(
                ArgumentMatchers.<Specification<Employee>>any(),
                any(Pageable.class)
        )).thenReturn(page);
        when(employeeMapper.mapToDTO(employee)).thenReturn(responseDTO);

        Page<EmployeeResponseDTO> result = employeeService.getAllEmployees(filter);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(employeeRepository).findAll(
                ArgumentMatchers.<Specification<Employee>>any(),
                any(Pageable.class)
        );
    }
}
