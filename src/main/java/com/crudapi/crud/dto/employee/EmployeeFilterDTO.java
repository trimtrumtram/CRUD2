package com.crudapi.crud.dto.employee;

import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.enums.sort.EmployeeSortField;
import com.crudapi.crud.enums.entityEnums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmployeeFilterDTO {

    @Schema(description = "Employee first name")
    private String firstName;
    @Schema(description = "Employee last name")
    private String lastName;
    @Schema(description = "Employee email")
    private String email;
    @Schema(description = "Employee role")
    private Role role;
    @Schema(description = "Page number for pagination", example = "0")
    private Integer page;
    @Schema(description = "Size of the page for pagination", example = "10")
    private Integer size;
    @Schema(description = "Field used for sorting", example = "LASTNAME")
    private EmployeeSortField sortField;
    @Schema(description = "Direction of sorting", example = "ASC")
    private SortDirection sortDirection;
}
