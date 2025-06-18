package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderDTO {

    @NotNull(message = "status is required")
    private OrderStatus status;
}
