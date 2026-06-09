package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.GetOrdersByCustomerUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetOrdersByCustomerUseCaseImpl implements GetOrdersByCustomerUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final OrderResponseMapper mapper;

    public GetOrdersByCustomerUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                          CustomerRepositoryPort customerRepositoryPort,
                                          OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerRepositoryPort = customerRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public List<OrderResponse> execute(UUID customerId) {
        customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new OrderDomainException(
                        "Customer not found with id: " + customerId));

        return orderRepositoryPort.findByCustomerId(customerId)
                .stream()
                .map(mapper::toOrderResponse)
                .toList();
    }
}