package com.muhrifqii;

import java.math.BigDecimal;
import java.util.List;

public class DataProvider {
    public static List<Customer> customers() {
        return List.of(
                new Customer("user1", "Alice", "alice@example.com"),
                new Customer("user2", "Bob", "bob@example.com"),
                new Customer("user3", "Charlie", "charlie@example.com"),
                new Customer("user4", "David", "david@example.com"),
                new Customer("user5", "Eve", "eve@example.com"));
    }

    public static List<Transaction> transactions() {
        return List.of(
                new Transaction("user1", new Money(Currency.USD, new BigDecimal(200))),
                new Transaction("user2", new Money(Currency.EUR, new BigDecimal(300))),
                new Transaction("user3", new Money(Currency.IDR, new BigDecimal(5_000_000))),
                new Transaction("user1", new Money(Currency.USD, new BigDecimal(150))),
                new Transaction("user3", new Money(Currency.USD, new BigDecimal(450))),
                new Transaction("user4", new Money(Currency.USD, new BigDecimal(1000))),
                new Transaction("user5", new Money(Currency.EUR, new BigDecimal(75))),
                new Transaction("user2", new Money(Currency.USD, new BigDecimal(120))),
                new Transaction("user1", new Money(Currency.IDR, new BigDecimal(500_000))),
                new Transaction("user5", new Money(Currency.IDR, new BigDecimal(25))),
                new Transaction("user4", new Money(Currency.USD, new BigDecimal(900))));
    }
}
