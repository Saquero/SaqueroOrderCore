package com.saquero.ordercore.application.mapper;

import com.saquero.ordercore.application.dto.OrderResponse;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper {

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount().getAmount(),
                order.getTotalAmount().getCurrency(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getStatus().name(),
                payment.getAmount().getAmount(),
                payment.getAmount().getCurrency(),
                payment.getProcessedAt()
        );
    }
}