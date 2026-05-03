package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.dto.CustomerResponse;
import com.saquero.ordercore.application.port.in.GetCustomerUseCase;
import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderDomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class GetCustomerUseCaseImpl implements GetCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public GetCustomerUseCaseImpl(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public CustomerResponse execute(UUID customerId) {
        return customerRepositoryPort.findById(customerId)
                .map(c -> new CustomerResponse(c.getId(), c.getName(), c.getEmail(), c.getCreatedAt()))
                .orElseThrow(() -> new OrderDomainException(
                        "Customer not found with id: " + customerId));
    }
}