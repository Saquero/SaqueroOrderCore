package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.application.port.out.PaymentRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.OrderStatus;
import com.saquero.ordercore.domain.model.Payment;
import com.saquero.ordercore.domain.model.PaymentStatus;
import com.saquero.ordercore.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessPaymentUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private PaymentRepositoryPort paymentRepositoryPort;

    @InjectMocks
    private ProcessPaymentUseCaseImpl processPaymentUseCase;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order(
                orderId,
                UUID.randomUUID(),
                new Money(new BigDecimal("99.99"), "EUR"),
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("Payment processing saves order and payment")
    void paymentProcessingSavesOrderAndPayment() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepositoryPort.save(any(Order.class))).thenReturn(order);
        when(paymentRepositoryPort.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        ProcessPaymentCommand command = new ProcessPaymentCommand(orderId);
        PaymentResponse response = processPaymentUseCase.execute(command);

        assertNotNull(response);
        assertEquals(orderId, response.getOrderId());
        assertTrue(
            response.getStatus().equals(PaymentStatus.SUCCESS.name()) ||
            response.getStatus().equals(PaymentStatus.FAILED.name())
        );
        verify(orderRepositoryPort, times(1)).save(any(Order.class));
        verify(paymentRepositoryPort, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("Throws OrderNotFoundException when order does not exist")
    void throwsExceptionWhenOrderNotFound() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.empty());

        ProcessPaymentCommand command = new ProcessPaymentCommand(orderId);

        assertThrows(OrderNotFoundException.class, () -> processPaymentUseCase.execute(command));
        verify(paymentRepositoryPort, never()).save(any());
    }

    @Test
    @DisplayName("Order status changes after payment processing")
    void orderStatusChangesAfterPayment() {
        when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepositoryPort.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));
        when(paymentRepositoryPort.save(any(Payment.class))).thenAnswer(inv -> inv.getArgument(0));

        ProcessPaymentCommand command = new ProcessPaymentCommand(orderId);
        processPaymentUseCase.execute(command);

        assertTrue(
            order.getStatus() == OrderStatus.PAID ||
            order.getStatus() == OrderStatus.FAILED
        );
    }
}