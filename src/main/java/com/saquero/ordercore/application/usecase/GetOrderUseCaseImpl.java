package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.GetOrderUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetOrderUseCaseImpl implements GetOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderResponseMapper mapper;

    public GetOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort, OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public OrderResponse execute(UUID orderId) {
        return orderRepositoryPort.findById(orderId)
                .map(mapper::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));
    }
}