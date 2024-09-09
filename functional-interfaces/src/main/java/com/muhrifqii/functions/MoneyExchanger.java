package com.muhrifqii.functions;

import com.muhrifqii.Money;
import java.util.Optional;

@FunctionalInterface
public interface MoneyExchanger {
    Optional<Money> exchange(Money money);
}
