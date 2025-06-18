package com.crudapi.crud.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class CreateProductDTO {

    @NotBlank(message = "name is required")
    private String name;

    private String description;

    @NotBlank(message = "price is required")
    private BigDecimal price;
}
