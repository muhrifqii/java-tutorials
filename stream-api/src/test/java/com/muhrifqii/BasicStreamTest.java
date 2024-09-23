package com.muhrifqii;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class BasicStreamTest {

    @Test
    public void whenGroupingByUserId_thenMapUniquely() {
        final var customers = DataProvider.customers();

        final var customersByUserId = customers.stream()
                .collect(Collectors.groupingBy(Customer::userId));

        for (var customerSet : customersByUserId.entrySet()) {
            assertEquals(1, customerSet.getValue().size());
            assertEquals(customerSet.getKey(),
                    customerSet.getValue()
                            .get(0)
                            .userId());
        }
    }

    @Test
    public void when_then() {
        final var customers = DataProvider.customers();
        final var transactions = DataProvider.transactions();

    }
}
