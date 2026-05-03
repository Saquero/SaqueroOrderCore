package com.saquero.ordercore.infrastructure.adapter.in.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderRequest {

    @NotNull(message = "customerId is required")
    private UUID customerId;

    @NotNull(message = "totalAmount is required")
    @DecimalMin(value = "0.01", message = "totalAmount must be greater than 0")
    private BigDecimal totalAmount;

    @NotBlank(message = "currency is required")
    @Size(min = 3, max = 3, message = "currency must be exactly 3 characters")
    private String currency;

    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }

    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public void setCurrency(String currency) { this.currency = currency; }
}