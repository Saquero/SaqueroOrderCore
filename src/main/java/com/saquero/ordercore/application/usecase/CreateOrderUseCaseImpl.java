package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.mapper.OrderResponseMapper;
import com.saquero.ordercore.application.port.in.CreateOrderUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.valueobject.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateOrderUseCaseImpl.class);

    private final OrderRepositoryPort orderRepositoryPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final OrderResponseMapper mapper;

    public CreateOrderUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                  CustomerRepositoryPort customerRepositoryPort,
                                  OrderResponseMapper mapper) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.customerRepositoryPort = customerRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public OrderResponse execute(CreateOrderCommand command) {
        log.debug("Creating order for customerId={} amount={} currency={}",
                command.getCustomerId(), command.getTotalAmount(), command.getCurrency());

        var customer = customerRepositoryPort
                .findById(command.getCustomerId())
                .orElseThrow(() -> new OrderDomainException(
                        "Customer not found with id: " + command.getCustomerId()));

        var totalAmount = new Money(command.getTotalAmount(), command.getCurrency());
        var order = new Order(UUID.randomUUID(), customer.getId(), totalAmount, LocalDateTime.now());
        var saved = orderRepositoryPort.save(order);

        log.info("Order created orderId={} customerId={} amount={}",
                saved.getId(), saved.getCustomerId(), saved.getTotalAmount());

        return mapper.toOrderResponse(saved);
    }
}