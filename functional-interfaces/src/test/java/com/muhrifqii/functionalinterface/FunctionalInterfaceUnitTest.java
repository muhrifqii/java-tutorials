package com.muhrifqii.functionalinterface;

import org.junit.jupiter.api.Test;

import com.muhrifqii.Currency;
import com.muhrifqii.Money;
import com.muhrifqii.functions.MoneyExchanger;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.*;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionalInterfaceUnitTest {

    @Test
    public void whenUsingMethodReference_thenOutputTheSame() {
        // static method reference
        assertEquals(10, Optional.of("10")
                .map(Integer::parseInt)
                .orElseThrow());

        // instance method reference
        final var str = "Hello World";
        assertEquals('H', Optional.of(0)
                .map(str::charAt)
                .orElseThrow());

        // instance method reference of type
        assertEquals("HELLO WORLD", Optional.of("hello world")
                .map(String::toUpperCase)
                .orElseThrow());

        // constructor reference
        assertEquals(3, Optional.of("Hey")
                .map(StringBuilder::new)
                .map(StringBuilder::length)
                .orElseThrow());
    }

    @Test
    public void whenUsingBuiltInFunctionalInterface_thenExecutedAccordingly() {
        final Function<String, Integer> stringToInteger = Integer::parseInt;
        assertEquals(10, Optional.of("10")
                .map(stringToInteger)
                .orElseThrow());

        final var bucket = new HashMap<String, Integer>();
        bucket.put("red", 1);
        bucket.put("blue", 2);
        bucket.put("green", 3);
        // BiConsumer
        bucket.forEach((color, count) -> assertNotEquals("black", color));
        // BiFunction
        bucket.replaceAll((color, count) -> count * 0);
        // BiConsumer
        bucket.forEach((color, count) -> assertEquals(0, count));

        // Supplier
        final Supplier<String> lazyStr = () -> "Hello World";
        assertEquals("Hello World", lazyStr.get());

        // Consumer
        final Consumer<String> consumerStr = message -> {
            assertEquals("Hello World", message);
        };
        consumerStr.accept("Hello World");

        // Predicate
        final Predicate<String> predicateStr = message -> message.contains("World");
        assertTrue(predicateStr.test("Hello World"));

        // BinaryOperator
        final BinaryOperator<Integer> binaryOperator = (a, b) -> a * b;
        assertEquals(100, binaryOperator.apply(10, 10));

        // UnaryOperator
        final UnaryOperator<Integer> unaryOperator = a -> a * a;
        assertEquals(100, unaryOperator.apply(10));

    }

    @Test
    public void whenUsingFunctionalInterface_thenExecutedAccordingly() {

        final Predicate<Money> expectIdr = m -> Currency.IDR.equals(m.currency());

        final MoneyExchanger idrToUsd = money -> Optional.ofNullable(money)
                .filter(expectIdr)
                .map(m -> new Money(Currency.USD,
                        m.amount().divide(BigDecimal.valueOf(15_000))));

        final MoneyExchanger idrToEur = money -> Optional.ofNullable(money)
                .filter(expectIdr)
                .map(m -> new Money(Currency.EUR,
                        m.amount().divide(BigDecimal.valueOf(17_000))));

        final Function<Money, Optional<Money>> functionIdrToEur = money -> Optional.ofNullable(money)
                .filter(expectIdr)
                .map(m -> new Money(Currency.EUR,
                        m.amount().divide(BigDecimal.valueOf(17_000))));

        final var moneyExchangedIdrToUsd = idrToUsd.exchange(
                new Money(Currency.IDR, BigDecimal.valueOf(150_000))).get();
        assertEquals(Currency.USD, moneyExchangedIdrToUsd.currency());
        assertEquals(10, moneyExchangedIdrToUsd.amount().intValue());

        final var moneyExchangeIdrToUsdFailed = idrToUsd.exchange(
                new Money(Currency.EUR, BigDecimal.valueOf(20_000)));
        assertTrue(moneyExchangeIdrToUsdFailed.isEmpty());

        final var idrEurSourceMoney = new Money(Currency.IDR, BigDecimal.valueOf(170_000));
        final var moneyExchangedIdrToEur = idrToEur.exchange(idrEurSourceMoney).get();
        assertEquals(Currency.EUR, moneyExchangedIdrToEur.currency());
        assertEquals(10, moneyExchangedIdrToEur.amount().intValue());

        final var moneyExchangedIdrToEurByFunction = functionIdrToEur.apply(idrEurSourceMoney).get();
        assertEquals(moneyExchangedIdrToEur, moneyExchangedIdrToEurByFunction);
    }

    @Test
    public void whenPassingLambdaToThread_thenLambdaInferredToRunnable() throws InterruptedException {
        final var message = new StringBuffer();
        final var latch = new CountDownLatch(1);

        final var virtualThread = Thread.ofVirtual().unstarted(() -> {
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            message.append("Who's there?");
            latch.countDown();
        });

        virtualThread.start();
        message.append("Knock knock!");

        latch.await(100, TimeUnit.MILLISECONDS);
        message.append("It's me!");

        virtualThread.join();

        assertEquals("Knock knock!Who's there?It's me!", message.toString());
        assertFalse(virtualThread.isAlive());
    }
}
