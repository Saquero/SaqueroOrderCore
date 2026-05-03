package com.saquero.ordercore.application.command;

public class CreateCustomerCommand {

    private final String name;
    private final String email;

    public CreateCustomerCommand(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}