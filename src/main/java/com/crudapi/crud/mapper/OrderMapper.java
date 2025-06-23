package com.crudapi.crud.mapper;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface OrderMapper {

    @Mapping(source = "client.id", target = "idClient")
    @Mapping(source = "client.lastName", target = "clientLastName")
    OrderResponseDTO mapToDTO(Order order);

    Order mapToEntity(CreateOrderDTO dto);
}
