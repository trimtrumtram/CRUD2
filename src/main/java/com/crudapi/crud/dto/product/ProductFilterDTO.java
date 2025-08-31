package com.crudapi.crud.dto.product;

import com.crudapi.crud.enums.sort.ProductSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilterDTO {

    @Schema(description = "Product name")
    private String name;
    @Schema(description = "Product start price")
    private BigDecimal startPrice;
    @Schema(description = "Product end price")
    private BigDecimal endPrice;
    @Schema(description = "Page number for pagination", example = "0")
    private Integer page;
    @Schema(description = "Size of the page for pagination", example = "10")
    private Integer size;
    @Schema(description = "Field used for sorting", example = "PRICE")
    private ProductSortField sortField;
    @Schema(description = "Direction of sorting", example = "ASC")
    private SortDirection sortDirection;
}
