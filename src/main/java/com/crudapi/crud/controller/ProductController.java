package com.crudapi.crud.controller;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.dto.product.UpdateProductDTO;
import com.crudapi.crud.enums.sort.ProductSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.mapper.filterMapper.ProductFilterMapper;
import com.crudapi.crud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Create a new product",
            description = "Creating a new product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not created")
            }
    )
    public ProductResponseDTO createProduct(@RequestBody CreateProductDTO dto) {
        return productService.createProduct(dto);
    }

    @PutMapping("product/{id}")
    @Operation(
            summary = "Update an existing product",
            description = "Updating an existing product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not updated")
            }
    )
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/product/{id}")
    @Operation(
            summary = "Delete an existing product",
            description = "Deleting an existing product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Product is not deleted")
            }
    )
    public boolean deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return true;
    }

    @GetMapping("/product/{id}")
    @Operation(
            summary = "Get a product by id",
            description = "Getting a product by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product found successfully"),
                    @ApiResponse(responseCode = "400", description = "Product not found")
            }
    )
    public ProductResponseDTO getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/product")
    @Operation(
            summary = "Get all products",
            description = "Getting all products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products found successfully"),
                    @ApiResponse(responseCode = "200", description = "Products not found")
            }
    )
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String nameFilter,
            @RequestParam(required = false) BigDecimal startPriceFilter,
            @RequestParam(required = false) BigDecimal endPriceFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ID") ProductSortField sortBy,
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
