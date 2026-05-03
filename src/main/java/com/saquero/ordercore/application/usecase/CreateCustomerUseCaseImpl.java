package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.CreateCustomerCommand;
import com.saquero.ordercore.application.dto.CustomerResponse;
import com.saquero.ordercore.application.port.in.CreateCustomerUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import com.saquero.ordercore.domain.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CreateCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public CustomerResponse execute(CreateCustomerCommand command) {
        if (customerRepositoryPort.existsByEmail(command.getEmail())) {
            throw new OrderDomainException(
                "Customer already exists with email: " + command.getEmail()
            );
        }

        Customer customer = new Customer(
                UUID.randomUUID(),
                command.getName(),
                command.getEmail(),
                LocalDateTime.now()
        );

        Customer saved = customerRepositoryPort.save(customer);

        return new CustomerResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getCreatedAt()
        );
    }
}