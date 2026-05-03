package com.saquero.ordercore.infrastructure.adapter.out.persistence.repository;

import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, UUID> {
    List<PaymentJpaEntity> findByOrderIdOrderByCreatedAtDesc(UUID orderId);
}