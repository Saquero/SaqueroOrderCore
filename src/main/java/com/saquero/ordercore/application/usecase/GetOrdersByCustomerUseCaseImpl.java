package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.GetOrdersByCustomerUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import com.saquero.ordercore.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetOrdersByCustomerUseCaseImpl implements GetOrdersByCustomerUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;

    public GetOrdersByCustomerUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                          CustomerRepositoryPort customerRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public List<OrderResponse> execute(UUID customerId) {
        customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new OrderDomainException(
                        "Customer not found with id: " + customerId));

        return orderRepositoryPort.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {
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