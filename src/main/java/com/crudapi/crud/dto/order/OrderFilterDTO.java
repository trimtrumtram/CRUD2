package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.OrderSortField;
import com.crudapi.crud.enums.OrderStatus;
import com.crudapi.crud.enums.SortDirection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFilterDTO {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private OrderStatus status;
    private Integer page;
    private Integer size;
    private OrderSortField sortField;
    private SortDirection sortDirection;
}
