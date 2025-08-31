package com.crudapi.crud.dto.client;

import com.crudapi.crud.dto.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;

@Data
public class UpdateClientDTO  {

    @Schema (description = "Client first name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema (description = "Client last name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema (description = "Client email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema (description = "Client phone number")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @Schema (description = "Client orders")
    private List<OrderResponseDTO> orders;
}
