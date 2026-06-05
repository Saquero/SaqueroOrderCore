package com.saquero.ordercore.infrastructure.adapter.out.payment;

import com.saquero.ordercore.application.port.out.PaymentGatewayPort;
import com.saquero.ordercore.domain.valueobject.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Stub implementation of PaymentGatewayPort for development and testing.
 * In production this would be replaced by a real payment provider adapter
 * (Stripe, Adyen, etc.) without touching any application or domain code.
 */
@Component
public class StubPaymentGatewayAdapter implements PaymentGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(StubPaymentGatewayAdapter.class);
    private static final double FAILURE_RATE = 0.2;

    @Override
    public PaymentResult process(UUID orderId, Money amount) {
        boolean success = Math.random() > FAILURE_RATE;
        String reference = "STUB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        log.info("StubPaymentGateway: orderId={} amount={} success={} reference={}",
                orderId, amount, success, reference);

        return new PaymentResult(success, reference);
    }
}