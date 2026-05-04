package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.CancelOrderUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import com.saquero.ordercore.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public CancelOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public OrderResponse execute(UUID orderId) {
        Order order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.cancel();

        Order saved = orderRepositoryPort.save(order);

        return new OrderResponse(
                saved.getId(),
                saved.getCustomerId(),
                saved.getStatus().name(),
                saved.getTotalAmount().getAmount(),
                saved.getTotalAmount().getCurrency(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}