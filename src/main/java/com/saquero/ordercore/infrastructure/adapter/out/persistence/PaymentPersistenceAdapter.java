package com.saquero.ordercore.infrastructure.adapter.out.persistence;

import com.saquero.ordercore.application.port.out.PaymentRepositoryPort;
import com.saquero.ordercore.domain.model.Payment;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.PaymentJpaEntity;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper.PaymentMapper;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.repository.PaymentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PaymentPersistenceAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentMapper paymentMapper;

    public PaymentPersistenceAdapter(PaymentJpaRepository paymentJpaRepository,
                                     PaymentMapper paymentMapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity entity = paymentMapper.toEntity(payment);
        PaymentJpaEntity saved = paymentJpaRepository.save(entity);
        return paymentMapper.toDomain(saved);
    }

    @Override
    public List<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderIdOrderByCreatedAtDesc(orderId)
                .stream()
                .map(paymentMapper::toDomain)
                .toList();
    }
}