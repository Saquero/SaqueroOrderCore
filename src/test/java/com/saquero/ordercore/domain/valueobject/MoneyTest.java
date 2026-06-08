package com.saquero.ordercore.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Money value object")
class MoneyTest {

    @Nested
    @DisplayName("Construction")
    class Construction {

        @Test
        @DisplayName("should create with valid amount and currency")
        void shouldCreateWithValidAmountAndCurrency() {
            Money money = new Money(new BigDecimal("100.00"), "EUR");
            assertThat(money.getAmount()).isEqualByComparingTo(new BigDecimal("100.0000"));
            assertThat(money.getCurrency()).isEqualTo("EUR");
        }

        @Test
        @DisplayName("should accept zero amount")
        void shouldAcceptZeroAmount() {
            Money money = new Money(BigDecimal.ZERO, "EUR");
            assertThat(money.getAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("should normalize currency to uppercase")
        void shouldNormalizeCurrencyToUppercase() {
            Money money = new Money(new BigDecimal("10.00"), "eur");
            assertThat(money.getCurrency()).isEqualTo("EUR");
        }

        @Test
        @DisplayName("should scale amount to 4 decimal places")
        void shouldScaleAmountTo4DecimalPlaces() {
            Money money = new Money(new BigDecimal("10.1"), "EUR");
            assertThat(money.getAmount().scale()).isEqualTo(4);
        }

        @Test
        @DisplayName("should reject negative amount")
        void shouldRejectNegativeAmount() {
            assertThatThrownBy(() -> new Money(new BigDecimal("-1.00"), "EUR"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("zero or positive");
        }

        @Test
        @DisplayName("should reject null amount")
        void shouldRejectNullAmount() {
            assertThatThrownBy(() -> new Money(null, "EUR"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("should reject blank currency")
        void shouldRejectBlankCurrency() {
            assertThatThrownBy(() -> new Money(new BigDecimal("10.00"), ""))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Currency");
        }

        @Test
        @DisplayName("should reject null currency")
        void shouldRejectNullCurrency() {
            assertThatThrownBy(() -> new Money(new BigDecimal("10.00"), null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Arithmetic")
    class Arithmetic {

        @Test
        @DisplayName("should add two Money values with same currency")
        void shouldAddSameCurrency() {
            Money a = new Money(new BigDecimal("10.00"), "EUR");
            Money b = new Money(new BigDecimal("5.50"), "EUR");
            assertThat(a.add(b).getAmount()).isEqualByComparingTo(new BigDecimal("15.50"));
        }

        @Test
        @DisplayName("should reject adding different currencies")
        void shouldRejectDifferentCurrencies() {
            Money eur = new Money(new BigDecimal("10.00"), "EUR");
            Money usd = new Money(new BigDecimal("10.00"), "USD");
            assertThatThrownBy(() -> eur.add(usd))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Currency mismatch");
        }
    }

    @Nested
    @DisplayName("Equality")
    class Equality {

        @Test
        @DisplayName("should be equal when amount and currency match")
        void shouldBeEqualWhenAmountAndCurrencyMatch() {
            Money a = new Money(new BigDecimal("10.00"), "EUR");
            Money b = new Money(new BigDecimal("10.00"), "EUR");
            assertThat(a).isEqualTo(b);
            assertThat(a.hashCode()).isEqualTo(b.hashCode());
        }

        @Test
        @DisplayName("should not be equal when currencies differ")
        void shouldNotBeEqualWhenCurrenciesDiffer() {
            Money eur = new Money(new BigDecimal("10.00"), "EUR");
            Money usd = new Money(new BigDecimal("10.00"), "USD");
            assertThat(eur).isNotEqualTo(usd);
        }
    }

    @Nested
    @DisplayName("Query methods")
    class QueryMethods {

        @Test
        @DisplayName("isGreaterThanZero should return true for positive amount")
        void shouldReturnTrueForPositiveAmount() {
            Money money = new Money(new BigDecimal("0.01"), "EUR");
            assertThat(money.isGreaterThanZero()).isTrue();
        }

        @Test
        @DisplayName("isGreaterThanZero should return false for zero")
        void shouldReturnFalseForZero() {
            Money money = new Money(BigDecimal.ZERO, "EUR");
            assertThat(money.isGreaterThanZero()).isFalse();
        }
    }
}