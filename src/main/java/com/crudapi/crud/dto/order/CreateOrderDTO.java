package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.entityEnums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateOrderDTO {

    @Schema(description = "Order status", example = "NEW")
    private OrderStatus status;
    @Schema(description = "Client ID")
    private Long clientId;
}
