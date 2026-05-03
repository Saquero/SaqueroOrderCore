package com.saquero.ordercore.infrastructure.adapter.out.persistence.mapper;

import com.saquero.ordercore.domain.model.Payment;
import com.saquero.ordercore.domain.model.PaymentStatus;
import com.saquero.ordercore.domain.valueobject.Money;
import com.saquero.ordercore.infrastructure.adapter.out.persistence.entity.PaymentJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toDomain(PaymentJpaEntity entity) {
        Payment payment = new Payment(
                entity.getId(),
                entity.getOrderId(),
                new Money(entity.getAmount(), entity.getCurrency()),
                entity.getCreatedAt()
        );
        if (PaymentStatus.SUCCESS.name().equals(entity.getStatus())) {
            payment.markSuccess();
        } else if (PaymentStatus.FAILED.name().equals(entity.getStatus())) {
            payment.markFailed();
        }
        return payment;
    }

    public PaymentJpaEntity toEntity(Payment payment) {
        return new PaymentJpaEntity(
                payment.getId(),
                payment.getOrderId(),
                payment.getStatus().name(),
                payment.getAmount().getAmount(),
                payment.getAmount().getCurrency(),
                payment.getProcessedAt(),
                payment.getCreatedAt()
        );
    }
}