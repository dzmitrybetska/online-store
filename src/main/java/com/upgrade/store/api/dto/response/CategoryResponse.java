package com.upgrade.store.api.dto.response;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        Boolean active,
        List<SubCategoryResponse> subCategories
) {
}
