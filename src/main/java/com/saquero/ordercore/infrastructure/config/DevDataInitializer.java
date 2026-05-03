package com.saquero.ordercore.infrastructure.config;

import com.saquero.ordercore.application.command.CreateCustomerCommand;
import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.CustomerResponse;
import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.port.in.CreateCustomerUseCase;
import com.saquero.ordercore.application.port.in.CreateOrderUseCase;
import com.saquero.ordercore.application.port.in.ProcessPaymentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final ProcessPaymentUseCase processPaymentUseCase;

    public DevDataInitializer(CreateCustomerUseCase createCustomerUseCase,
                              CreateOrderUseCase createOrderUseCase,
                              ProcessPaymentUseCase processPaymentUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.createOrderUseCase = createOrderUseCase;
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("=== Loading dev seed data ===");

        CustomerResponse customer1 = createCustomerUseCase.execute(
                new CreateCustomerCommand("Alice Dev", "alice@dev.com"));
        CustomerResponse customer2 = createCustomerUseCase.execute(
                new CreateCustomerCommand("Bob Dev", "bob@dev.com"));

        log.info("Created customers: {}, {}", customer1.getId(), customer2.getId());

        OrderResponse order1 = createOrderUseCase.execute(
                new CreateOrderCommand(customer1.getId(), new BigDecimal("149.99"), "EUR"));
        OrderResponse order2 = createOrderUseCase.execute(
                new CreateOrderCommand(customer1.getId(), new BigDecimal("299.00"), "EUR"));
        OrderResponse order3 = createOrderUseCase.execute(
                new CreateOrderCommand(customer2.getId(), new BigDecimal("59.50"), "USD"));

        log.info("Created orders: {}, {}, {}", order1.getId(), order2.getId(), order3.getId());

        processPaymentUseCase.execute(new ProcessPaymentCommand(order1.getId()));
        processPaymentUseCase.execute(new ProcessPaymentCommand(order2.getId()));

        log.info("=== Dev seed data loaded ===");
    }
}