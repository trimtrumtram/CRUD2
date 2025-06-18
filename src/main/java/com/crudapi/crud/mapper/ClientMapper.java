package com.crudapi.crud.mapper;

import com.crudapi.crud.dto.client.ClientResponseDTO;
import com.crudapi.crud.dto.client.CreateClientDTO;
import com.crudapi.crud.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "id", source = "id")
    ClientResponseDTO mapToDTO(Client client);
    @Mapping(target = "id", ignore = true)
    Client mapToEntity(CreateClientDTO dto);
}
