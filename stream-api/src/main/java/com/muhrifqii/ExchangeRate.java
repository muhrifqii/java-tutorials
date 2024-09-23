package com.muhrifqii;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.UnaryOperator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExchangeRate {
    private static final BigDecimal USD_IDR = BigDecimal.valueOf(15_000);
    private static final BigDecimal USD_EUR = BigDecimal.valueOf(1.12);
    private static final BigDecimal EUR_IDR = BigDecimal.valueOf(17_000);

    public static final Map<Currency, UnaryOperator<Money>> IDR_EXCHANGE_RATES = Map.of(
            Currency.USD, m -> new Money(Currency.USD, m.amount().divide(USD_IDR)),
            Currency.IDR, UnaryOperator.identity(),
            Currency.EUR, m -> new Money(Currency.EUR, m.amount().divide(EUR_IDR)));

    public static final Map<Currency, UnaryOperator<Money>> TO_USD_EXCHANGE_RATES = Map.of(
            Currency.USD, UnaryOperator.identity(),
            Currency.IDR, m -> new Money(Currency.USD, m.amount().multiply(USD_IDR)),
            Currency.EUR, m -> new Money(Currency.USD, m.amount().multiply(USD_EUR)));

}
