package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    List<Order> findAll(OrderStatus status, int page, int size);
    long countAll(OrderStatus status);
    Map<String, Long> countByStatus();
    List<Order> findByCustomerId(UUID customerId);
}