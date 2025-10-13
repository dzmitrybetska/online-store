package com.upgrade.store.api.dto.response;

import com.upgrade.store.domain.model.ProductStatus;

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
