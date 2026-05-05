package com.saquero.ordercore.infrastructure.adapter.out.persistence;

import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.OrderStatus;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper.OrderMapper;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.repository.OrderJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<Order> findAll(OrderStatus status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if (status != null) {
            return orderJpaRepository.findByStatus(status.name(), pageable)
                    .stream().map(orderMapper::toDomain).toList();
        }
        return orderJpaRepository.findAll(pageable)
                .stream().map(orderMapper::toDomain).toList();
    }

    @Override
    public long countAll(OrderStatus status) {
        if (status != null) {
            return orderJpaRepository.countByStatus(status.name());
        }
        return orderJpaRepository.count();
    }

    @Override
    public Map<String, Long> countByStatus() {
        Map<String, Long> result = new HashMap<>();
        orderJpaRepository.countGroupByStatus()
                .forEach(row -> result.put((String) row[0], (Long) row[1]));
        return result;
    }

    @Override
    public List<Order> findByCustomerId(UUID customerId) {
        return orderJpaRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(orderMapper::toDomain)
                .toList();
    }
}