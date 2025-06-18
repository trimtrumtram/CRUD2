package com.crudapi.crud.dto.employee;

import com.crudapi.crud.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateEmployeeDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 symbols")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
