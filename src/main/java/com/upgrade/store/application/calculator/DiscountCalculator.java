package com.upgrade.store.application.calculator;

import com.upgrade.store.api.exception.InvalidDiscountConfigurationException;
import com.upgrade.store.application.util.MoneyUtils;
import com.upgrade.store.domain.model.Discount;
import com.upgrade.store.domain.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

@Component
public class DiscountCalculator {

    public BigDecimal calculatePrice(Product product) {
        return product.getDiscounts().stream()
                .filter(Objects::nonNull)
                .filter(this::isDiscountActive)
                .max(Comparator.comparing(Discount::getDiscountPercent))
                .map(discount -> MoneyUtils.applyDiscount(product.getPrice(), discount.getDiscountPercent()))
                .orElse(product.getPrice());
    }

    private boolean isDiscountActive(Discount discount) {
        if (discount.getDiscountStart() == null || discount.getDiscountEnd() == null) {
            throw new InvalidDiscountConfigurationException("Discount start and end dates must not be null.");
        }
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(discount.getDiscountStart()) && !now.isAfter(discount.getDiscountEnd());
    }
}
