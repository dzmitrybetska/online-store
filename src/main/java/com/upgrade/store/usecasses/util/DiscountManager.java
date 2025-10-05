package com.upgrade.store.usecasses.util;

import com.upgrade.store.api.exception.InvalidDiscountConfigurationException;
import com.upgrade.store.persistence.model.Discount;
import com.upgrade.store.persistence.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DiscountManager {

    public BigDecimal calculatePrice(Product product) {
        Discount discount = product.getDiscount();
        if (!isDiscountActive(discount)) return product.getPrice();

        BigDecimal discountPercent = discount.getDiscountPercent();
        if (discountPercent == null)
            throw new InvalidDiscountConfigurationException("Discount percent cannot be null when discount is active.");

        return MoneyUtils.applyDiscount(product.getPrice(), discountPercent);
    }

    private boolean isDiscountActive(Discount discount) {
        if (discount == null) {
            return false;
        }

        if (discount.getDiscountStart() == null || discount.getDiscountEnd() == null) {
            throw new InvalidDiscountConfigurationException("Discount start and end dates must not be null.");
        }

        LocalDateTime now = LocalDateTime.now();

        return !now.isBefore(discount.getDiscountStart()) && !now.isAfter(discount.getDiscountEnd());
    }
}
