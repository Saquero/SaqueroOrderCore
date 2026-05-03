package com.saquero.ordercore.domain.exception;

public class OrderNotFoundException extends OrderDomainException {

    public OrderNotFoundException(String orderId) {
        super("Order not found with id: " + orderId);
    }
}
