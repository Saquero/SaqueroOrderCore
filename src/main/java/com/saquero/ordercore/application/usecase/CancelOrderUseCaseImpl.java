package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.CancelOrderUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CancelOrderUseCaseImpl implements CancelOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderResponseMapper mapper;

    public CancelOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort, OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public OrderResponse execute(UUID orderId) {
        var order = orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        order.cancel();

        return mapper.toOrderResponse(orderRepositoryPort.save(order));
    }
}