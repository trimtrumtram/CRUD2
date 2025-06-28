package com.crudapi.crud.mapper.filterMapper;

import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.enums.sort.ProductSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ProductFilterMapper {

    @Mapping(source = "nameFilter", target = "name")
    @Mapping(source = "startPriceFilter", target = "startPrice")
    @Mapping(source = "endPriceFilter", target = "endPrice")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "sortBy", target = "sortField")
    @Mapping(source = "sortDirection", target = "sortDirection")
    ProductFilterDTO toDTO (
            String nameFilter,
            BigDecimal startPriceFilter,
            BigDecimal endPriceFilter,
            Integer page,
            Integer size,
            ProductSortField sortBy,
            SortDirection sortDirection
    );
}
