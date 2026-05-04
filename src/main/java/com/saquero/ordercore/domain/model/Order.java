package com.saquero.ordercore.domain.model;

import com.saquero.ordercore.domain.exception.InvalidOrderStateTransitionException;
import com.saquero.ordercore.domain.valueobject.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final UUID customerId;
    private OrderStatus status;
    private final Money totalAmount;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public Order(UUID id, UUID customerId, Money totalAmount, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.CREATED;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public Order(UUID id, UUID customerId, OrderStatus status, Money totalAmount,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void startProcessing() {
        if (this.status != OrderStatus.CREATED) {
            throw new InvalidOrderStateTransitionException(
                this.status.name(), OrderStatus.PROCESSING.name()
            );
        }
        this.status = OrderStatus.PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPaid() {
        if (this.status != OrderStatus.PROCESSING) {
            throw new InvalidOrderStateTransitionException(
                this.status.name(), OrderStatus.PAID.name()
            );
        }
        this.status = OrderStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        if (this.status != OrderStatus.PROCESSING) {
            throw new InvalidOrderStateTransitionException(
                this.status.name(), OrderStatus.FAILED.name()
            );
        }
        this.status = OrderStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status != OrderStatus.CREATED) {
            throw new InvalidOrderStateTransitionException(
                this.status.name(), OrderStatus.CANCELLED.name()
            );
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(this.status);
    }

    public boolean canBePaid() {
        return OrderStatus.CREATED.equals(this.status);
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public OrderStatus getStatus() { return status; }
    public Money getTotalAmount() { return totalAmount; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}