package com.crudapi.crud.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDTO {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotBlank(message = "price is required")
    private BigDecimal price;
}
