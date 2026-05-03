package com.saquero.ordercore.domain.model;

import com.saquero.ordercore.domain.valueobject.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final UUID orderId;
    private PaymentStatus status;
    private final Money amount;
    private LocalDateTime processedAt;
    private final LocalDateTime createdAt;

    public Payment(UUID id, UUID orderId, Money amount, LocalDateTime createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.createdAt = createdAt;
    }

    public void markSuccess() {
        this.status = PaymentStatus.SUCCESS;
        this.processedAt = LocalDateTime.now();
    }

    public void markFailed() {
        this.status = PaymentStatus.FAILED;
        this.processedAt = LocalDateTime.now();
    }

    public boolean isSuccessful() {
        return PaymentStatus.SUCCESS.equals(this.status);
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public PaymentStatus getStatus() { return status; }
    public Money getAmount() { return amount; }
    public LocalDateTime getProcessedAt() { return processedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
