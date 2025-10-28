package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record DiscountProductsUpdateRequest(
        @Schema(description = "Product IDs to add to the discount")
        Set<Long> productIdsToAdd,

        @Schema(description = "Product IDs to remove from the discount")
        Set<Long> productIdsToRemove
) {
}
