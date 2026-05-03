package com.saquero.ordercore.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderResponse {

    private final UUID id;
    private final UUID customerId;
    private final String status;
    private final BigDecimal totalAmount;
    private final String currency;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OrderResponse(UUID id, UUID customerId, String status,
                         BigDecimal totalAmount, String currency,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}