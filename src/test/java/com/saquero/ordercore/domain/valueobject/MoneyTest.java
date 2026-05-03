package com.saquero.ordercore.domain.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("Money is created with correct amount and currency")
    void moneyIsCreatedCorrectly() {
        Money money = new Money(new BigDecimal("100.00"), "EUR");
        assertEquals(new BigDecimal("100.0000"), money.getAmount());
        assertEquals("EUR", money.getCurrency());
    }

    @Test
    @DisplayName("Currency is stored in uppercase")
    void currencyIsStoredUppercase() {
        Money money = new Money(new BigDecimal("50.00"), "eur");
        assertEquals("EUR", money.getCurrency());
    }

    @Test
    @DisplayName("Two Money objects with same values are equal")
    void moneyEqualityWorks() {
        Money m1 = new Money(new BigDecimal("99.99"), "EUR");
        Money m2 = new Money(new BigDecimal("99.99"), "EUR");
        assertEquals(m1, m2);
    }

    @Test
    @DisplayName("Money addition works correctly")
    void moneyAdditionWorks() {
        Money m1 = new Money(new BigDecimal("100.00"), "EUR");
        Money m2 = new Money(new BigDecimal("50.00"), "EUR");
        Money result = m1.add(m2);
        assertEquals(new BigDecimal("150.0000"), result.getAmount());
    }

    @Test
    @DisplayName("Cannot add money with different currencies")
    void cannotAddDifferentCurrencies() {
        Money eur = new Money(new BigDecimal("100.00"), "EUR");
        Money usd = new Money(new BigDecimal("100.00"), "USD");
        assertThrows(IllegalArgumentException.class, () -> eur.add(usd));
    }

    @Test
    @DisplayName("Cannot create money with negative amount")
    void cannotCreateMoneyWithNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(new BigDecimal("-1.00"), "EUR"));
    }

    @Test
    @DisplayName("Cannot create money with blank currency")
    void cannotCreateMoneyWithBlankCurrency() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(new BigDecimal("10.00"), ""));
    }

    @Test
    @DisplayName("isGreaterThanZero returns true for positive amount")
    void isGreaterThanZeroReturnsTrueForPositiveAmount() {
        Money money = new Money(new BigDecimal("0.01"), "EUR");
        assertTrue(money.isGreaterThanZero());
    }
}