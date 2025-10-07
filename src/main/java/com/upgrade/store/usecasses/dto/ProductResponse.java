package com.upgrade.store.usecasses.dto;

import com.upgrade.store.persistence.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        BigDecimal finalPrice,
        Integer quantityInStock,
        String sku,
        String ean,
        Long categoryId,
        List<String> imageUrls,
        ProductStatus status,
        List<Long> discountIDs
) {
}
