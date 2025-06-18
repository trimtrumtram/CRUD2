package com.crudapi.crud.service;

import com.crudapi.crud.dto.order.CreateOrderDTO;
import com.crudapi.crud.dto.order.OrderFilterDTO;
import com.crudapi.crud.dto.order.OrderResponseDTO;
import com.crudapi.crud.dto.order.UpdateOrderDTO;
import com.crudapi.crud.enums.OrderSortField;
import com.crudapi.crud.enums.SortDirection;
import com.crudapi.crud.mapper.OrderMapper;
import com.crudapi.crud.model.Order;
import com.crudapi.crud.specification.OrderSpecification;
import com.crudapi.crud.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponseDTO createOrder (CreateOrderDTO dto) {
        Order order = orderMapper.mapToEntity(dto);
        order.setCreationDateTime(LocalDateTime.now());
        return orderMapper.mapToDTO(orderRepository.save(order));
    }

    public OrderResponseDTO updateOrder (Long id, UpdateOrderDTO dto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(dto.getStatus());
        return orderMapper.mapToDTO(orderRepository.save(order));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderResponseDTO getOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Page<OrderResponseDTO> getOrders(OrderFilterDTO filter) {

        try {
            OrderSortField sortField = filter.getSortField() != null ? filter.getSortField() : OrderSortField.CREATION_DATE_TIME;
            Sort sort = Sort.by(sortField.getSortBy());
            sort = filter.getSortDirection() == SortDirection.DESC ? sort.descending() : sort.ascending();

            int page = filter.getPage() != null ? filter.getPage() : 0;
            int size = filter.getSize() != null ? filter.getSize() : 10;
            Pageable pageable = PageRequest.of(page, size, sort);

            return orderRepository.findAll(
                    OrderSpecification.filterOrder(
                            filter.getStartDate(),
                            filter.getEndDate(),
                            filter.getStatus()
                    ),
                    pageable
            ).map(orderMapper::mapToDTO);
        }catch (Exception e) {
            throw new IllegalArgumentException("Invalid filter " + e.getMessage());
        }
    }
}
