package com.saquero.ordercore.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentResponse {

    private final UUID id;
    private final UUID orderId;
    private final String status;
    private final BigDecimal amount;
    private final String currency;
    private final LocalDateTime processedAt;

    public PaymentResponse(UUID id, UUID orderId, String status,
                           BigDecimal amount, String currency,
                           LocalDateTime processedAt) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.processedAt = processedAt;
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}