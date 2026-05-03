package com.saquero.ordercore.infrastructure.adapter.out.persistence;

import com.saquero.ordercore.application.port.out.CustomerRepositoryPort;
import com.saquero.ordercore.domain.model.Customer;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper.CustomerMapper;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;

    public CustomerPersistenceAdapter(CustomerJpaRepository customerJpaRepository,
                                      CustomerMapper customerMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer save(Customer customer) {
        return customerMapper.toDomain(
                customerJpaRepository.save(customerMapper.toEntity(customer))
        );
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerJpaRepository.findById(id)
                .map(customerMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmail(email);
    }
}