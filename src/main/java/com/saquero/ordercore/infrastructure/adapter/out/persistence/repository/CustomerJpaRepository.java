package com.saquero.ordercore.infrastructure.adapter.out.persistence.repository;

import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, UUID> {
}