package com.saquero.ordercore.application.port.out;

import com.saquero.ordercore.domain.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    List<Payment> findByOrderId(UUID orderId);
}