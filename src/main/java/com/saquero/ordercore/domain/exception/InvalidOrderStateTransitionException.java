package com.saquero.ordercore.domain.exception;

public class InvalidOrderStateTransitionException extends OrderDomainException {

    public InvalidOrderStateTransitionException(String from, String to) {
        super("Invalid order state transition from " + from + " to " + to);
    }
}
