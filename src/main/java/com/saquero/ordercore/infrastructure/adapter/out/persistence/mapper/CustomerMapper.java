package com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper;

import com.saquero.ordercore.domain.model.Customer;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.CustomerJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCreatedAt()
        );
    }

    public CustomerJpaEntity toEntity(Customer customer) {
        return new CustomerJpaEntity(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt()
        );
    }
}