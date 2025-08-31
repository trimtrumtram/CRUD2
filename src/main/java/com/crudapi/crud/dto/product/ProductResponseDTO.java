package com.crudapi.crud.dto.product;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDTO {

    @Schema(description = "Product ID")
    private Long id;
    @Schema(description = "Product name")
    private String name;
    @Schema(description = "Product description")
    private String description;
    @Schema(description = "Product price")
    private BigDecimal price;
}
