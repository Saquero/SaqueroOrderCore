package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryPort {
    Optional<Customer> findById(UUID id);
}