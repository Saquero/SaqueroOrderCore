package com.saquero.ordercore.infrastructure.adapter.out.persistence.repository;

import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
}