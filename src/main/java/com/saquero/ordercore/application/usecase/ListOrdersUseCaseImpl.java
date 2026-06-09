package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.dto.PageResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.ListOrdersUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.model.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListOrdersUseCaseImpl implements ListOrdersUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderResponseMapper mapper;

    public ListOrdersUseCaseImpl(OrderRepositoryPort orderRepositoryPort, OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<OrderResponse> execute(OrderStatus status, int page, int size) {
        List<OrderResponse> orders = orderRepositoryPort.findAll(status, page, size)
                .stream()
                .map(mapper::toOrderResponse)
                .toList();

        long total = orderRepositoryPort.countAll(status);
        return new PageResponse<>(orders, page, size, total);
    }
}