package com.crudapi.crud.dto.client;

import com.crudapi.crud.dto.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateClientDTO {

    @Schema(description = "Client first name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "Client last name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "Client email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Client phone number")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @Schema(description = "Client orders")
    private List<OrderResponseDTO> orders;
}
