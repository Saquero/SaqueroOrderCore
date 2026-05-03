package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.command.CreateOrderCommand;
import com.saquero.ordercore.application.dto.OrderResponse;

public interface CreateOrderUseCase {
    OrderResponse execute(CreateOrderCommand command);
}