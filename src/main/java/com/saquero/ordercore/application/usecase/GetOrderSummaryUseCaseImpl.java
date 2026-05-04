package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderSummaryResponse;
import com.saquero.ordercore.application.port.in.GetOrderSummaryUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class GetOrderSummaryUseCaseImpl implements GetOrderSummaryUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderSummaryUseCaseImpl(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public OrderSummaryResponse execute() {
        Map<String, Long> byStatus = orderRepositoryPort.countByStatus();
        long total = byStatus.values().stream().mapToLong(Long::longValue).sum();
        return new OrderSummaryResponse(total, byStatus);
    }
}