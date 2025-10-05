package com.upgrade.store.usecasses.dto;

import com.upgrade.store.persistence.model.Category;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantityInStock,
        String sku,
        String ean,
        Category category,
        List<String> imageUrls,
        Boolean active
) {
}
