package com.upgrade.store.application.util;

import com.upgrade.store.api.exception.InvalidMoneyArgumentException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class MoneyUtils {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final int SCALE = 2;
    private static final int INTERMEDIATE_SCALE = 10;

    public static BigDecimal applyDiscount(BigDecimal price, BigDecimal discountPercent) {

        if (price == null) {
            throw new InvalidMoneyArgumentException("Price must not be null");
        }
        if (discountPercent == null) {
            throw new InvalidMoneyArgumentException("Discount percent must not be null");
        }
        if (price.signum() < 0) {
            throw new InvalidMoneyArgumentException("Price must be non-negative: " + price);
        }
        if (discountPercent.signum() < 0) {
            throw new InvalidMoneyArgumentException("Discount percent must not be negative: " + discountPercent);
        }
        if (discountPercent.compareTo(ONE_HUNDRED) > 0) {
            throw new InvalidMoneyArgumentException("Discount percent must not be greater than 100: " + discountPercent);
        }
        if (discountPercent.compareTo(ONE_HUNDRED) == 0) {
            return BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal multiplier = ONE_HUNDRED
                .subtract(discountPercent)
                .divide(ONE_HUNDRED, INTERMEDIATE_SCALE, RoundingMode.HALF_UP);

        return price.multiply(multiplier).setScale(SCALE, RoundingMode.HALF_UP);
    }
}
