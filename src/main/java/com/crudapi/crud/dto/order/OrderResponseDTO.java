package com.crudapi.crud.dto.order;

import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.enums.entityEnums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class OrderResponseDTO {

    @Schema(description = "Order ID")
    private Long id;
    @Schema(description = "Order creation date and time")
    private LocalDateTime creationDateTime;
    @Schema(description = "Order status", example = "NEW")
    private OrderStatus status;
    @Schema(description = "Client ID")
    private Long clientId;
    @Schema(description = "Order products")
    private List<ProductResponseDTO> products;
}
