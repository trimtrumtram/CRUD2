package com.crudapi.crud.service;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.dto.product.UpdateProductDTO;
import com.crudapi.crud.enums.ProductSortField;
import com.crudapi.crud.enums.SortDirection;
import com.crudapi.crud.mapper.ProductMapper;
import com.crudapi.crud.model.Product;
import com.crudapi.crud.repository.ProductRepository;
import com.crudapi.crud.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.mapToEntity(dto);
        return productMapper.mapToDTO(productRepository.save(product));
    }

    public ProductResponseDTO updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return productMapper.mapToDTO(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductResponseDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Page<ProductResponseDTO> getAllProducts(ProductFilterDTO filter) {
        try {
            ProductSortField sortField = filter.getSortField() != null ? filter.getSortField() : ProductSortField.ID;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            return productRepository.findAll(
                    ProductSpecification.filterProduct(
                            filter.getName(),
                            filter.getStartPrice(),
                            filter.getEndPrice()
                    ),
                    pageable
            ).map(productMapper::mapToDTO);
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid filter" + e.getMessage());
        }
    }
}
