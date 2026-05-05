package com.saquero.ordercore.infrastructure.adapter.out.persistence.repository;

import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.OrderJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    Page<OrderJpaEntity> findByStatus(String status, Pageable pageable);
    long countByStatus(String status);
    List<OrderJpaEntity> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);

    @Query("SELECT o.status, COUNT(o) FROM OrderJpaEntity o GROUP BY o.status")
    List<Object[]> countGroupByStatus();
}