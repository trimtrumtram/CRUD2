package com.crudapi.crud.controller;

import com.crudapi.crud.enums.SortDirection;
import com.crudapi.crud.enums.EmployeeSortField;
import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.mapper.EmployeeFilterMapper;
import com.crudapi.crud.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeFilterMapper employeeFilterMapper;

    @PostMapping("/employee")
    public EmployeeResponseDTO createEmployee(@RequestBody CreateEmployeeDTO dto) {
        return employeeService.createEmployee(dto);
    }

    @PutMapping("/employee/{id}")
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeDTO dto) {
        return employeeService.updateEmployee(id, dto);
    }

    @DeleteMapping("/employee/{id}")
    public boolean deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return true;
    }

    @GetMapping("/employee")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) String firstNameFilter,
            @RequestParam(required = false) String lastNameFilter,
            @RequestParam(required = false) String roleFilter,
            @RequestParam(required = false) String emailFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "lastName") EmployeeSortField sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection)
    {
        EmployeeFilterDTO filter = employeeFilterMapper.toDTO(
                firstNameFilter,
                lastNameFilter,
                roleFilter,
                emailFilter,
                page,
                size,
                sortBy,
                sortDirection
        );
        Page<EmployeeResponseDTO> employees = employeeService.getAllEmployees(filter);
        return ResponseEntity.ok(employees);
    }
}
