package com.crudapi.crud.dto.client;

import com.crudapi.crud.dto.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ClientResponseDTO {

    @Schema(description = "Client ID")
    private Long id;
    @Schema(description = "Client first name")
    private String firstName;
    @Schema(description = "Client last name")
    private String lastName;
    @Schema(description = "Client email")
    private String email;
    @Schema(description = "Client phone number")
    private String phone;
    @Schema(description = "Client orders")
    private List<OrderResponseDTO> orders;
}
