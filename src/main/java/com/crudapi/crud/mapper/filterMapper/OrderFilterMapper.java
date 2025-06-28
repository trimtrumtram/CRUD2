package com.crudapi.crud.mapper.filterMapper;

import com.crudapi.crud.dto.order.OrderFilterDTO;
import com.crudapi.crud.enums.sort.OrderSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface OrderFilterMapper {

    @Mapping(source = "startDateFilter", target = "startDate")
    @Mapping(source = "endDateFilter", target = "endDate")
    @Mapping(source = "statusFilter", target = "status")
    @Mapping(source = "page", target = "page")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "sortBy", target = "sortField")
    @Mapping(source = "sortDirection", target = "sortDirection")
    OrderFilterDTO toDTO(
            LocalDateTime startDateFilter,
            LocalDateTime endDateFilter,
            String statusFilter,
            Integer page,
            Integer size,
            OrderSortField sortBy,
            SortDirection sortDirection
    );
}
