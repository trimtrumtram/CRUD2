package com.crudapi.crud.mapper.entityMapper;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToEntity(CreateProductDTO dto);

    ProductResponseDTO mapToDTO(Product product);
}
