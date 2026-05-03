package com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper;

import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.OrderStatus;
import com.saquero.ordercore.domain.valueobject.Money;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toDomain(OrderJpaEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                OrderStatus.valueOf(entity.getStatus()),
                new Money(entity.getTotalAmount(), entity.getCurrency()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OrderJpaEntity toEntity(Order order) {
        return new OrderJpaEntity(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount().getAmount(),
                order.getTotalAmount().getCurrency(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}