package com.muhrifqii;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpenderTierCheck {
    public static String classifySpendingTierInUsd(Spending spending) {
        return Optional.ofNullable(spending)
                .map(Spending::amount)
                .filter(money -> money.currency() == Currency.USD)
                .map(Money::amount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) > 0)
                .map(SpenderTierCheck::spendintTierInUsd)
                .orElse("Zero Spender");
    }

    private static String spendintTierInUsd(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(200)) < 0) {
            return "Low Spender";
        } else if (amount.compareTo(new BigDecimal(500)) < 0) {
            return "Mid Spender";
        } else if (amount.compareTo(new BigDecimal(1000)) < 0) {
            return "High Spender";
        } else {
            return "Whale";
        }
    }
}
