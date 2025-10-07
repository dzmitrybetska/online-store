package com.upgrade.store.usecasses.util;

import com.upgrade.store.api.exception.InvalidDiscountConfigurationException;
import com.upgrade.store.persistence.model.Discount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

@Component
public class DiscountManager {

    public BigDecimal calculatePrice(BigDecimal price, Set<Discount> discounts) {
        return discounts.stream()
                .filter(Objects::nonNull)
                .filter(this::isDiscountActive)
                .max(Comparator.comparing(Discount::getDiscountPercent))
                .map(discount -> MoneyUtils.applyDiscount(price, discount.getDiscountPercent()))
                .orElse(price);
    }

    private boolean isDiscountActive(Discount discount) {
        if (discount.getDiscountStart() == null || discount.getDiscountEnd() == null) {
            throw new InvalidDiscountConfigurationException("Discount start and end dates must not be null.");
        }
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(discount.getDiscountStart()) && !now.isAfter(discount.getDiscountEnd());
    }
}
