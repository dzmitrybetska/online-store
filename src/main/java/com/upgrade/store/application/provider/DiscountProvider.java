package com.upgrade.store.application.provider;

import com.upgrade.store.domain.model.Product;

import java.math.BigDecimal;

public interface DiscountProvider {

    BigDecimal calculatePrice(Product product);
}
