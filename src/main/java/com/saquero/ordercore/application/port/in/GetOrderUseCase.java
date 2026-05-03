package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.OrderResponse;

import java.util.UUID;

public interface GetOrderUseCase {
    OrderResponse execute(UUID orderId);
}