package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.GetOrderUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import com.saquero.ordercore.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetOrderUseCaseImpl implements GetOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public OrderResponse execute(UUID orderId) {
        Order order = orderRepositoryPort
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return new OrderResponse(
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