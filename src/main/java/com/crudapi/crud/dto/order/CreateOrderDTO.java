package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.OrderStatus;
import com.crudapi.crud.model.Client;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderDTO {

    private LocalDateTime creationDateTime;
    private OrderStatus status;
    private Client client;
}
