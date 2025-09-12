package com.crudapi.crud.service;

import com.crudapi.crud.dto.product.CreateProductDTO;
import com.crudapi.crud.dto.product.ProductFilterDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.dto.product.UpdateProductDTO;
import com.crudapi.crud.enums.sort.ProductSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.mapper.entityMapper.ProductMapper;
import com.crudapi.crud.model.Order;
import com.crudapi.crud.model.Product;
import com.crudapi.crud.repository.OrderRepository;
import com.crudapi.crud.repository.ProductRepository;
import com.crudapi.crud.specification.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.orderRepository = orderRepository;
    }

    public ProductResponseDTO addProductToOrder(Long orderId, long productId) {
        log.info("Добавление продукта в заказ: orderId={}, productId={}", orderId, productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Продукт не найден: id={}", productId);
                    return new RuntimeException("Product not found");
                });

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Заказ не найден: id={}", orderId);
                    return new RuntimeException("Order not found");
                });

        order.getProducts().add(product);
        orderRepository.save(order);

        log.info("Продукт id={} успешно добавлен в заказ id={}", productId, orderId);
        return productMapper.mapToDTO(product);
    }

    public ProductResponseDTO createProduct(CreateProductDTO dto) {
        log.info("Создание продукта: name={}", dto.getName());
        log.debug("Детали CreateProductDTO: {}", dto);

        Product product = productMapper.mapToEntity(dto);
        Product savedProduct = productRepository.save(product);

        log.info("Продукт создан: id={}", savedProduct.getId());
        return productMapper.mapToDTO(savedProduct);
    }

    public ProductResponseDTO updateProduct(Long id, UpdateProductDTO dto) {
        log.info("Обновление продукта id={}", id);
        log.debug("Детали UpdateProductDTO: {}", dto);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Продукт не найден: id={}", id);
                    return new RuntimeException("Product not found");
                });

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());

        Product updatedProduct = productRepository.save(product);
        log.info("Продукт успешно обновлён: id={}", updatedProduct.getId());

        return productMapper.mapToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        log.info("Удаление продукта id={}", id);

        if (!productRepository.existsById(id)) {
            log.error("Попытка удалить несуществующий продукт id={}", id);
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);
        log.info("Продукт успешно удалён: id={}", id);
    }

    public ProductResponseDTO findById(Long id) {
        log.info("Получение продукта по id={}", id);

        return productRepository.findById(id)
                .map(product -> {
                    log.debug("Найден продукт: {}", product);
                    return productMapper.mapToDTO(product);
                })
                .orElseThrow(() -> {
                    log.error("Продукт не найден: id={}", id);
                    return new RuntimeException("Product not found");
                });
    }

    public Page<ProductResponseDTO> getAllProducts(ProductFilterDTO filter) {
        log.info("Получение списка продуктов с фильтром: {}", filter);
        try {
            ProductSortField sortField = filter.getSortField() != null ? filter.getSortField() : ProductSortField.ID;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            log.debug("Параметры пагинации: page={}, size={}, sort={}", page, size, sort);

            Page<ProductResponseDTO> result = productRepository.findAll(
                    ProductSpecification.filterProduct(
                            filter.getName(),
                            filter.getStartPrice(),
                            filter.getEndPrice()
                    ),
                    pageable
            ).map(productMapper::mapToDTO);

            log.info("Найдено продуктов: {}", result.getTotalElements());
            return result;
        } catch (Exception e) {
            log.error("Ошибка при получении списка продуктов: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Invalid filter: " + e.getMessage());
        }
    }
}
