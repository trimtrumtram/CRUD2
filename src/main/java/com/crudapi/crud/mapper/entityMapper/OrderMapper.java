package com.crudapi.crud.mapper.entityMapper;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.model.Client;
import com.crudapi.crud.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "client.id", target = "clientId")
    OrderResponseDTO mapToDTO(Order order);

    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapClient")
    Order mapToEntity(CreateOrderDTO dto);

    @Named("mapClient")
    default Client mapClient(Long clientId) {
        if(clientId == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientId);
        return client;
    }
}
