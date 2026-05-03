package com.saquero.ordercore.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID id, String name, String email, LocalDateTime createdAt) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name must not be blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Customer email must not be blank");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
