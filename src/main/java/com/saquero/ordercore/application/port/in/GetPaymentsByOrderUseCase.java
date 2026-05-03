package com.saquero.ordercore.application.port.in;

import com.saquero.ordercore.application.dto.PaymentResponse;

import java.util.List;
import java.util.UUID;

public interface GetPaymentsByOrderUseCase {
    List<PaymentResponse> execute(UUID orderId);
}