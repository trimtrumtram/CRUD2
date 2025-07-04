package com.crudapi.crud.dto.client;

import com.crudapi.crud.dto.order.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ClientResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<OrderResponseDTO> orders;
}
