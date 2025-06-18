package com.crudapi.crud.service;

import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.enums.EmployeeSortField;
import com.crudapi.crud.mapper.EmployeeMapper;
import com.crudapi.crud.specification.EmployeeSpecification;
import com.crudapi.crud.enums.SortDirection;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.model.Employee;
import com.crudapi.crud.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponseDTO createEmployee(CreateEmployeeDTO dto) {
        if(employeeRepository.existsEmployeeByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Employee employee = employeeMapper.mapToEntity(dto);
        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.mapToDTO(savedEmployee);
    }

    public EmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeDTO dto) {
        Employee employee = findEmployee(id, dto);
        validateEmail(employee, dto.getEmail());
        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapToDTO(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public Page<EmployeeResponseDTO> getAllEmployees (EmployeeFilterDTO filter) {
        try {
            EmployeeSortField sortField = filter.getSortField() != null ? filter.getSortField() : EmployeeSortField.ID;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            return employeeRepository.findAll(
                    EmployeeSpecification.filterEmployee(
                            filter.getFirstName(),
                            filter.getLastName(),
                            String.valueOf(filter.getRole()),
                            filter.getEmail()
                    ),
                    pageable
            ).map(employeeMapper::mapToDTO);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid filter" + e.getMessage());
        }
    }

    private Employee findEmployee(Long id, UpdateEmployeeDTO dto) {
        if(id != null) {
            return employeeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Employee with id " + id + " not found"));
        }
        if(dto.getEmail() != null) {
            return employeeRepository.findEmployeeByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Employee with email " + dto.getEmail() + " not found"));
        }
        else throw new IllegalArgumentException("Email or id is required");
    }

    private void validateEmail(Employee employee, String newEmail) {
        if(newEmail != null && !newEmail.isBlank() && !newEmail.equals(employee.getEmail())) {
            if(employeeRepository.existsEmployeeByEmail(newEmail)) {
                throw new IllegalArgumentException("Employee with email " + newEmail + " already exists");
            }
        }
    }
}
