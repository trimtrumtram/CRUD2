package com.crudapi.crud.dto.product;

import com.crudapi.crud.enums.ProductSortField;
import com.crudapi.crud.enums.SortDirection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFilterDTO {

    private String name;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
    private Integer page;
    private Integer size;
    private ProductSortField sortField;
    private SortDirection sortDirection;
}
