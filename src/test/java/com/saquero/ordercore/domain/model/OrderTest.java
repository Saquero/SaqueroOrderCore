package com.saquero.ordercore.domain.model;

import com.saquero.ordercore.domain.exception.InvalidOrderStateTransitionException;
import com.saquero.ordercore.domain.valueobject.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Order domain model")
class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Money(new BigDecimal("100.00"), "EUR"),
                LocalDateTime.now()
        );
    }

    @Nested
    @DisplayName("Initial state")
    class InitialState {

        @Test
        @DisplayName("should be CREATED on instantiation")
        void shouldBeCreatedOnInstantiation() {
            assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);
        }

        @Test
        @DisplayName("should have correct total amount")
        void shouldHaveCorrectTotalAmount() {
            assertThat(order.getTotalAmount().getAmount())
                    .isEqualByComparingTo(new BigDecimal("100.0000"));
        }
    }

    @Nested
    @DisplayName("State transitions — happy path")
    class HappyPath {

        @Test
        @DisplayName("CREATED -> PROCESSING should succeed")
        void shouldTransitionToProcessing() {
            order.startProcessing();
            assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);
        }

        @Test
        @DisplayName("PROCESSING -> PAID should succeed")
        void shouldTransitionToPaid() {
            order.startProcessing();
            order.markAsPaid();
            assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
        }

        @Test
        @DisplayName("PROCESSING -> FAILED should succeed")
        void shouldTransitionToFailed() {
            order.startProcessing();
            order.markAsFailed();
            assertThat(order.getStatus()).isEqualTo(OrderStatus.FAILED);
        }

        @Test
        @DisplayName("CREATED -> CANCELLED should succeed")
        void shouldTransitionToCancelled() {
            order.cancel();
            assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        }

        @Test
        @DisplayName("updatedAt should change after transition")
        void shouldUpdateTimestampOnTransition() {
            LocalDateTime before = order.getUpdatedAt();
            order.startProcessing();
            assertThat(order.getUpdatedAt()).isAfterOrEqualTo(before);
        }
    }

    @Nested
    @DisplayName("State transitions — invalid transitions")
    class InvalidTransitions {

        @Test
        @DisplayName("PAID -> PROCESSING should throw")
        void shouldRejectProcessingFromPaid() {
            order.startProcessing();
            order.markAsPaid();
            assertThatThrownBy(() -> order.startProcessing())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }

        @Test
        @DisplayName("CREATED -> PAID directly should throw")
        void shouldRejectPaidFromCreated() {
            assertThatThrownBy(() -> order.markAsPaid())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }

        @Test
        @DisplayName("CANCELLED -> PROCESSING should throw")
        void shouldRejectProcessingFromCancelled() {
            order.cancel();
            assertThatThrownBy(() -> order.startProcessing())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }

        @Test
        @DisplayName("PROCESSING -> CANCELLED should throw")
        void shouldRejectCancelFromProcessing() {
            order.startProcessing();
            assertThatThrownBy(() -> order.cancel())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }

        @Test
        @DisplayName("FAILED -> PAID should throw")
        void shouldRejectPaidFromFailed() {
            order.startProcessing();
            order.markAsFailed();
            assertThatThrownBy(() -> order.markAsPaid())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }

        @Test
        @DisplayName("PAID -> CANCELLED should throw")
        void shouldRejectCancelFromPaid() {
            order.startProcessing();
            order.markAsPaid();
            assertThatThrownBy(() -> order.cancel())
                    .isInstanceOf(InvalidOrderStateTransitionException.class);
        }
    }

    @Nested
    @DisplayName("Query methods")
    class QueryMethods {

        @Test
        @DisplayName("isPaid should return true only when PAID")
        void isPaidShouldReturnTrueOnlyWhenPaid() {
            assertThat(order.isPaid()).isFalse();
            order.startProcessing();
            order.markAsPaid();
            assertThat(order.isPaid()).isTrue();
        }

        @Test
        @DisplayName("canBePaid should return true only when CREATED")
        void canBePaidShouldReturnTrueOnlyWhenCreated() {
            assertThat(order.canBePaid()).isTrue();
            order.startProcessing();
            assertThat(order.canBePaid()).isFalse();
        }
    }
}