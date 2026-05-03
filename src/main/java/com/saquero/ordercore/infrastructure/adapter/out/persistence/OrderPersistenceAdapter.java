package com.saquero.ordercore.infrastructure.adapter.out.persistence;

import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper.OrderMapper;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.repository.OrderJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderMapper orderMapper;

    public OrderPersistenceAdapter(OrderJpaRepository orderJpaRepository,
                                   OrderMapper orderMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = orderMapper.toEntity(order);
        OrderJpaEntity saved = orderJpaRepository.save(entity);
        return orderMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderJpaRepository.findById(id)
                .map(orderMapper::toDomain);
    }
}