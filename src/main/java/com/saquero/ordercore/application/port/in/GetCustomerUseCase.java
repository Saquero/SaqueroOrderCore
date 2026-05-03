package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.CustomerResponse;

import java.util.UUID;

public interface GetCustomerUseCase {
    CustomerResponse execute(UUID customerId);
}