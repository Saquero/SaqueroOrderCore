package com.saquero.ordercore.infrastructure.exception;

import org.slf4j.MDC;

import java.time.LocalDateTime;

public class ApiError {

    private final int status;
    private final String error;
    private final String message;
    private final LocalDateTime timestamp;
    private final String correlationId;

    public ApiError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.correlationId = MDC.get("correlationId");
    }

    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getCorrelationId() { return correlationId; }
}