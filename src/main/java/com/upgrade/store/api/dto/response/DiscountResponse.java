package com.upgrade.store.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record DiscountResponse(
        Long id,
        BigDecimal discountPercent,
        LocalDateTime discountStart,
        LocalDateTime discountEnd,
        Set<Long> productIds
) {
}
