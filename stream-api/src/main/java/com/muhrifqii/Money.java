package com.muhrifqii;

import java.math.BigDecimal;

public record Money(Currency currency, BigDecimal amount) {
}
