package com.saquero.ordercore.infrastructure.adapter.out.persistence.repository;

import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    Page<OrderJpaEntity> findByStatus(String status, Pageable pageable);
    Page<OrderJpaEntity> findAll(Pageable pageable);
    long countByStatus(String status);
}