package com.crudapi.crud.dto.employee;

import com.crudapi.crud.enums.entityEnums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeResponseDTO {
    @Schema(description = "Employee ID")
    private Long id;
    @Schema(description = "Employee first name")
    private String firstName;
    @Schema(description = "Employee last name")
    private String lastName;
    @Schema(description = "Employee email")
    private String email;
    @Schema(description = "Employee password")
    private String password;
    @Schema(description = "Employee role", example = "ADMIN")
    private Role role;
}
