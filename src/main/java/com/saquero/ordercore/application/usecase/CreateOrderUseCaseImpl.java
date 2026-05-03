package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.CreateOrderUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import com.saquero.ordercore.domain.model.Customer;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.valueobject.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;

    public CreateOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                  CustomerRepositoryPort customerRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public OrderResponse execute(CreateOrderCommand command) {
        Customer customer = customerRepositoryPort
                .findById(command.getCustomerId())
                .orElseThrow(() -> new OrderDomainException(
                        "Customer not found with id: " + command.getCustomerId()));

        Money totalAmount = new Money(command.getTotalAmount(), command.getCurrency());

        Order order = new Order(
                UUID.randomUUID(),
                customer.getId(),
                totalAmount,
                LocalDateTime.now()
        );

        Order saved = orderRepositoryPort.save(order);
        return toResponse(saved);
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