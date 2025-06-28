package com.crudapi.crud.controller;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderFilterDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.dto.order.UpdateOrderDTO;
import com.crudapi.crud.dto.product.ProductResponseDTO;
import com.crudapi.crud.enums.sort.OrderSortField;
import com.crudapi.crud.enums.sort.SortDirection;
import com.crudapi.crud.mapper.filterMapper.OrderFilterMapper;
import com.crudapi.crud.model.Order;
import com.crudapi.crud.repository.OrderRepository;
import com.crudapi.crud.service.OrderService;
import com.crudapi.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderFilterMapper orderFilterMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;


    @PostMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<ProductResponseDTO> addProductToOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ProductResponseDTO dto = productService.addProductToOrder(order, productId);
        orderRepository.save(order);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/orders")
    public OrderResponseDTO createOrder(@RequestBody CreateOrderDTO dto) {
        return orderService.createOrder(dto);
    }

    @PutMapping("/order/{id}")
    public OrderResponseDTO updateOrder(@PathVariable Long id, @RequestBody UpdateOrderDTO dto) {
        return orderService.updateOrder(id, dto);
    }

    @DeleteMapping("/order/{id}")
    public boolean deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return true;
    }

    @GetMapping("/order/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/order")
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(required = false) LocalDateTime startDateFilter,
            @RequestParam(required = false) LocalDateTime endDateFilter,
            @RequestParam(required = false) String statusFilter,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "name") OrderSortField sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection)
    {
        OrderFilterDTO filter = orderFilterMapper.toDTO(
                startDateFilter,
                endDateFilter,
                statusFilter,
                page,
                size,
                sortBy,
                sortDirection
        );
        Page<OrderResponseDTO> orders = orderService.getOrders(filter);
        return ResponseEntity.ok(orders);
    }
}
