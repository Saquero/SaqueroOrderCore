package com.saquero.ordercore.application.command;

import java.util.UUID;

public class ProcessPaymentCommand {

    private final UUID orderId;

    public ProcessPaymentCommand(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() { return orderId; }
}