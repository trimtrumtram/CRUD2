package com.crudapi.crud.mapper;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface OrderMapper {

    OrderResponseDTO mapToDTO(Order order);

    Order mapToEntity(CreateOrderDTO dto);
}
