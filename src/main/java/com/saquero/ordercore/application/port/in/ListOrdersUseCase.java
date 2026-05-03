package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.dto.PageResponse;
import com.saquero.ordercore.domain.model.OrderStatus;

public interface ListOrdersUseCase {
    PageResponse<OrderResponse> execute(OrderStatus status, int page, int size);
}