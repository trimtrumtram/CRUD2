package com.crudapi.crud.controller;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.dto.product.UpdateProductDTO;
import com.crudapi.crud.enums.ProductSortField;
import com.crudapi.crud.enums.SortDirection;
import com.crudapi.crud.mapper.ProductFilterMapper;
import com.crudapi.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductFilterMapper productFilterMapper;

    @PostMapping("/product")
    public ProductResponseDTO createProduct(@RequestBody CreateProductDTO dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("product/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/product/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return true;
    }

    @GetMapping("/product/{id}")
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/product")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) BigDecimal startPriceFilter,
            @RequestParam(required = false) BigDecimal endPriceFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") ProductSortField sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection
    ) {
        ProductFilterDTO filter = productFilterMapper.toDTO(
                nameFilter,
                startPriceFilter,
                endPriceFilter,
                page,
                size,
                sortBy,
                sortDirection
        );
        Page<ProductResponseDTO> products = productService.getAllProducts(filter);
        return ResponseEntity.ok(products);
    }
}
