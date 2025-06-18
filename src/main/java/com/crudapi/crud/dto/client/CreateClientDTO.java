package com.crudapi.crud.dto.client;

import com.crudapi.crud.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateClientDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private List<Order> orders;
}
