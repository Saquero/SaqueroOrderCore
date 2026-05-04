package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.OrderResponse;

import java.util.UUID;

public interface CancelOrderUseCase {
    OrderResponse execute(UUID orderId);
}