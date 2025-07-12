package com.crudapi.crud.dto.order;

import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.enums.entityEnums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class OrderResponseDTO {

    private Long id;
    private LocalDateTime creationDateTime;
    private OrderStatus status;
    private Long clientId;
    private List<ProductResponseDTO> products;
}
