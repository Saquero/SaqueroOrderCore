package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.OrderSummaryResponse;

public interface GetOrderSummaryUseCase {
    OrderSummaryResponse execute();
}