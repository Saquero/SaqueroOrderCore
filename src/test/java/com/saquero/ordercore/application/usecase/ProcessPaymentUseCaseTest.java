package com.saquero.ordercore.application.usecase;

import com.saquero.ordercore.application.command.ProcessPaymentCommand;
import com.saquero.ordercore.application.dto.PaymentResponse;
import com.saquero.ordercore.application.port.out.OrderRepositoryPort;
import com.saquero.ordercore.application.port.out.PaymentGatewayPort;
import com.saquero.ordercore.application.port.out.PaymentRepositoryPort;
import com.saquero.ordercore.domain.exception.OrderNotFoundException;
import com.saquero.ordercore.domain.model.Order;
import com.saquero.ordercore.domain.model.OrderStatus;
import com.saquero.ordercore.domain.model.Payment;
import com.saquero.ordercore.domain.model.PaymentStatus;
import com.saquero.ordercore.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProcessPaymentUseCase")
class ProcessPaymentUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private PaymentRepositoryPort paymentRepositoryPort;

    @Mock
    private PaymentGatewayPort paymentGatewayPort;

    @InjectMocks
    private ProcessPaymentUseCaseImpl useCase;

    private Order order;
    private UUID orderId;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order(
                orderId,
                UUID.randomUUID(),
                new Money(new BigDecimal("150.00"), "EUR"),
                LocalDateTime.now()
        );
    }

    @Nested
    @DisplayName("Successful payment")
    class SuccessfulPayment {

        @Test
        @DisplayName("should mark order as PAID when gateway succeeds")
        void shouldMarkOrderAsPaidWhenGatewaySucceeds() {
            when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
            when(paymentGatewayPort.process(any(), any()))
                    .thenReturn(new PaymentGatewayPort.PaymentResult(true, "REF-001"));
            when(paymentRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));
            when(orderRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));

            useCase.execute(new ProcessPaymentCommand(orderId));

            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderRepositoryPort).save(orderCaptor.capture());
            assertThat(orderCaptor.getValue().getStatus()).isEqualTo(OrderStatus.PAID);
        }

        @Test
        @DisplayName("should save payment with SUCCESS status")
        void shouldSavePaymentWithSuccessStatus() {
            when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
            when(paymentGatewayPort.process(any(), any()))
                    .thenReturn(new PaymentGatewayPort.PaymentResult(true, "REF-001"));
            when(paymentRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));
            when(orderRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));

            PaymentResponse response = useCase.execute(new ProcessPaymentCommand(orderId));

            assertThat(response.getStatus()).isEqualTo(PaymentStatus.SUCCESS.name());
        }
    }

    @Nested
    @DisplayName("Failed payment")
    class FailedPayment {

        @Test
        @DisplayName("should mark order as FAILED when gateway fails")
        void shouldMarkOrderAsFailedWhenGatewayFails() {
            when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
            when(paymentGatewayPort.process(any(), any()))
                    .thenReturn(new PaymentGatewayPort.PaymentResult(false, "REF-002"));
            when(paymentRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));
            when(orderRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));

            useCase.execute(new ProcessPaymentCommand(orderId));

            ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
            verify(orderRepositoryPort).save(orderCaptor.capture());
            assertThat(orderCaptor.getValue().getStatus()).isEqualTo(OrderStatus.FAILED);
        }

        @Test
        @DisplayName("should save payment with FAILED status")
        void shouldSavePaymentWithFailedStatus() {
            when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.of(order));
            when(paymentGatewayPort.process(any(), any()))
                    .thenReturn(new PaymentGatewayPort.PaymentResult(false, "REF-002"));
            when(paymentRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));
            when(orderRepositoryPort.save(any())).thenAnswer(i -> i.getArgument(0));

            PaymentResponse response = useCase.execute(new ProcessPaymentCommand(orderId));

            assertThat(response.getStatus()).isEqualTo(PaymentStatus.FAILED.name());
        }
    }

    @Nested
    @DisplayName("Order not found")
    class OrderNotFound {

        @Test
        @DisplayName("should throw OrderNotFoundException when order does not exist")
        void shouldThrowWhenOrderNotFound() {
            when(orderRepositoryPort.findById(orderId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> useCase.execute(new ProcessPaymentCommand(orderId)))
                    .isInstanceOf(OrderNotFoundException.class);

            verify(paymentGatewayPort, never()).process(any(), any());
            verify(paymentRepositoryPort, never()).save(any());
        }
    }
}