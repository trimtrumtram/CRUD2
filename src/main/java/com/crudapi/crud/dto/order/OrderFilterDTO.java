package com.crudapi.crud.dto.order;

import com.crudapi.crud.enums.sort.OrderSortField;
import com.crudapi.crud.enums.entityEnums.OrderStatus;
import com.crudapi.crud.enums.sort.SortDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFilterDTO {

    @Schema(description = "Order start date")
    private LocalDateTime startDate;
    @Schema(description = "Order end date")
    private LocalDateTime endDate;
    @Schema(description = "Order status", example = "NEW")
    private OrderStatus status;
    @Schema(description = "Page number for pagination", example = "0")
    private Integer page;
    @Schema(description = "Size of the page for pagination", example = "10")
    private Integer size;
    @Schema(description = "Field used for sorting", example = "CREATION_DATE")
    private OrderSortField sortField;
    @Schema(description = "Direction of sorting", example = "ASC")
    private SortDirection sortDirection;
}
