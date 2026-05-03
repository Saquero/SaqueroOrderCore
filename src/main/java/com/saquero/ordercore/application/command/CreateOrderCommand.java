package com.saquero.ordercore.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderCommand {

    private final UUID customerId;
    private final BigDecimal totalAmount;
    private final String currency;

    public CreateOrderCommand(UUID customerId, BigDecimal totalAmount, String currency) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }

    public UUID getCustomerId() { return customerId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public String getCurrency() { return currency; }
}