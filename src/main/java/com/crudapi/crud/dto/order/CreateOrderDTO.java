package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.entityEnums.OrderStatus;
import lombok.Data;

@Data
public class CreateOrderDTO {

    private OrderStatus status;
    private Long clientId;
}
