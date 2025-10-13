package com.upgrade.store.application.provider.impl;

import com.upgrade.store.application.calculator.DiscountCalculator;
import com.upgrade.store.application.provider.DiscountProvider;
import com.upgrade.store.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DiscountProviderImpl implements DiscountProvider {

    private final DiscountCalculator discountCalculator;

    public BigDecimal calculatePrice(Product product) {
        return discountCalculator.calculatePrice(product);
    }
}
