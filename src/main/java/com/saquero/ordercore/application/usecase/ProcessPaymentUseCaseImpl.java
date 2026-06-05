package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.port.in.ProcessPaymentUseCase;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.application.port.out.PaymentGatewayPort;
import com.saquero.ordercore.application.port.out.PaymentRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ProcessPaymentUseCaseImpl implements ProcessPaymentUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentGatewayPort paymentGatewayPort;

    public ProcessPaymentUseCaseImpl(OrderRepositoryPort orderRepositoryPort,
                                     PaymentRepositoryPort paymentRepositoryPort,
                                     PaymentGatewayPort paymentGatewayPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.paymentGatewayPort = paymentGatewayPort;
    }

    @Override
    public PaymentResponse execute(ProcessPaymentCommand command) {
        Order order = orderRepositoryPort
                .findById(command.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(command.getOrderId().toString()));

        order.startProcessing();

        Payment payment = new Payment(
                UUID.randomUUID(),
                order.getId(),
                order.getTotalAmount(),
                LocalDateTime.now()
        );

        PaymentGatewayPort.PaymentResult result = paymentGatewayPort.process(
                order.getId(),
                order.getTotalAmount()
        );

        if (result.success()) {
            payment.markSuccess();
            order.markAsPaid();
        } else {
            payment.markFailed();
            order.markAsFailed();
        }

        orderRepositoryPort.save(order);
        Payment saved = paymentRepositoryPort.save(payment);

        return new PaymentResponse(
                saved.getId(),
                saved.getOrderId(),
                saved.getStatus().name(),
                saved.getAmount().getAmount(),
                saved.getAmount().getCurrency(),
                saved.getProcessedAt()
        );
    }
}