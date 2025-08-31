package com.crudapi.crud.dto.employee;

import com.crudapi.crud.enums.entityEnums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateEmployeeDTO {

    @Schema(description = "Employee first name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "Employee last name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "Employee email")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Schema(description = "Employee password")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 symbols")
    private String password;

    @Schema(description = "Employee role", example = "ADMIN")
    @NotNull(message = "Role is required")
    private Role role;
}
