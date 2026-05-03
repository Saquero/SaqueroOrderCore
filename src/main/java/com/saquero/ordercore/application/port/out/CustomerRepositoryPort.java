package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    boolean existsByEmail(String email);
}