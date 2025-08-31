package com.crudapi.crud.controller;

import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.enums.sort.EmployeeSortField;
import com.crudapi.crud.dto.employee.CreateEmployeeDTO;
import com.crudapi.crud.dto.employee.EmployeeFilterDTO;
import com.crudapi.crud.dto.employee.EmployeeResponseDTO;
import com.crudapi.crud.dto.employee.UpdateEmployeeDTO;
import com.crudapi.crud.mapper.filterMapper.EmployeeFilterMapper;
import com.crudapi.crud.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Create a new employee",
            description = "Creating a new employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee created successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not created")
            }
    )
    public EmployeeResponseDTO createEmployee(@RequestBody CreateEmployeeDTO dto) {
        return employeeService.createEmployee(dto);
    }

    @PutMapping("/employee/{id}")
    @Operation(
            summary = "Update an existing employee",
            description = "Updating an existing employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not updated")
            }
    )
    public EmployeeResponseDTO updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeeDTO dto) {
        return employeeService.updateEmployee(id, dto);
    }

    @DeleteMapping("/employee/{id}")
    @Operation(
            summary = "Delete an existing employee",
            description = "Deleting an existing employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Employee is not deleted")
            }
    )
    public boolean deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return true;
    }

    @GetMapping("/employee")
    @Operation(
            summary = "Get all employees",
            description = "Getting all employees",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employees found successfully"),
                    @ApiResponse(responseCode = "400", description = "Employees not found")
            }
    )
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) String firstNameFilter,
            @RequestParam(required = false) String lastNameFilter,
            @RequestParam(required = false) String roleFilter,
            @RequestParam(required = false) String emailFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "LASTNAME") EmployeeSortField sortBy,
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
