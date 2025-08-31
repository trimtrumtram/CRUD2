package com.crudapi.crud.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    @Schema(description = "Product name")
    @NotBlank(message = "name is required")
    private String name;

    @Schema(description = "Product description")
    private String description;

    @Schema(description = "Product price")
    @NotBlank(message = "price is required")
    private BigDecimal price;
}
