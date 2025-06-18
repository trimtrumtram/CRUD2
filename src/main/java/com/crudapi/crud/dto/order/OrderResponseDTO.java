package com.crudapi.crud.dto.order;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class OrderResponseDTO {

    private Long id;
    private LocalDateTime creationDateTime;
    private OrderStatus status;
    private ClientResponseDTO client;
}
