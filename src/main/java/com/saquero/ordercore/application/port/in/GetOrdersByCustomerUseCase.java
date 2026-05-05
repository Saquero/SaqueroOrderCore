package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface GetOrdersByCustomerUseCase {
    List<OrderResponse> execute(UUID customerId);
}