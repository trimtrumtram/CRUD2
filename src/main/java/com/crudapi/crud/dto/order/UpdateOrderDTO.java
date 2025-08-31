package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.entityEnums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderDTO {

    @Schema(description = "Order status", example = "NEW")
    @NotNull(message = "status is required")
    private OrderStatus status;
}
