package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.valueobject.Money;

import java.util.UUID;

public interface PaymentGatewayPort {

    PaymentResult process(UUID orderId, Money amount);

    record PaymentResult(boolean success, String gatewayReference) {}
}