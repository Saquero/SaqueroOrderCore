package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.GetPaymentsByOrderUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.application.port.out.PaymentRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetPaymentsByOrderUseCaseImpl implements GetPaymentsByOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final OrderResponseMapper mapper;

    public GetPaymentsByOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                         PaymentRepositoryPort paymentRepositoryPort,
                                         OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public List<PaymentResponse> execute(UUID orderId) {
        orderRepositoryPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return paymentRepositoryPort.findByOrderId(orderId)
                .stream()
                .map(mapper::toPaymentResponse)
                .toList();
    }
}