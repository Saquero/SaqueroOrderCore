package com.saquero.ordercore.domain.model;

import com.saquero.ordercore.domain.exception.InvalidOrderStateTransitionException;
import com.saquero.ordercore.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Money(new BigDecimal("99.99"), "EUR"),
                LocalDateTime.now()
        );
    }

    @Test
    @DisplayName("New order should have CREATED status")
    void newOrderShouldHaveCreatedStatus() {
        assertEquals(OrderStatus.CREATED, order.getStatus());
    }

    @Test
    @DisplayName("Order transitions from CREATED to PROCESSING")
    void orderTransitionsFromCreatedToProcessing() {
        order.startProcessing();
        assertEquals(OrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    @DisplayName("Order transitions from PROCESSING to PAID")
    void orderTransitionsFromProcessingToPaid() {
        order.startProcessing();
        order.markAsPaid();
        assertEquals(OrderStatus.PAID, order.getStatus());
        assertTrue(order.isPaid());
    }

    @Test
    @DisplayName("Order transitions from PROCESSING to FAILED")
    void orderTransitionsFromProcessingToFailed() {
        order.startProcessing();
        order.markAsFailed();
        assertEquals(OrderStatus.FAILED, order.getStatus());
    }

    @Test
    @DisplayName("Cannot pay order that is not in PROCESSING state")
    void cannotPayOrderNotInProcessingState() {
        assertThrows(InvalidOrderStateTransitionException.class, () -> order.markAsPaid());
    }

    @Test
    @DisplayName("Cannot process payment on already PAID order")
    void cannotStartProcessingOnPaidOrder() {
        order.startProcessing();
        order.markAsPaid();
        assertThrows(InvalidOrderStateTransitionException.class, () -> order.startProcessing());
    }

    @Test
    @DisplayName("Cannot fail order that is not in PROCESSING state")
    void cannotFailOrderNotInProcessingState() {
        assertThrows(InvalidOrderStateTransitionException.class, () -> order.markAsFailed());
    }
}