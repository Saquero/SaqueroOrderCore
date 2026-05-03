package com.saquero.ordercore.infrastructure.adapter.in.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCustomerRequest {

    @NotBlank(message = "name is required")
    @Size(max = 150, message = "name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}