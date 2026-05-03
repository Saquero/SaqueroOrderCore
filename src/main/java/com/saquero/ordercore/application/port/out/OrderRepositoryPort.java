package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.model.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
}