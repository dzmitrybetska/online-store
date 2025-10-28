package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record DiscountRequest(
        @NotNull(message = "Required field")
        @Schema(defaultValue = "5", description = "Discount percentage value (0–100)")
        BigDecimal discountPercent,

        @NotNull(message = "Required field")
        @FutureOrPresent(message = "The discount start date cannot be earlier than the current date.")
        @Schema(example = "2026-03-01T00:00:00", description = "Discount start date and time")
        LocalDateTime discountStart,

        @NotNull(message = "Required field")
        @FutureOrPresent(message = "The discount start date cannot be earlier than the current date.")
        @Schema(example = "2026-03-20T00:00:00", description = "Discount end date and time")
        LocalDateTime discountEnd,

        @Schema(description = "Set of product IDs to apply the discount to")
        Set<Long> productIds
) {
    @AssertTrue(message = "The end date of the discount cannot be earlier than the start date.")
    private boolean isValidDate() {
        if (discountStart == null || discountEnd == null) {
            return true;
        }
        return discountEnd.isAfter(discountStart);
    }
}
