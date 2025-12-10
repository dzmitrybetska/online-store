package com.upgrade.store.api.dto.response;

import java.util.List;

public record CategoryResponse(
        Long id,
        Long parentId,
        List<SubCategoryResponse> subCategories,
        Long userId,
        String name,
        String code,
        Boolean active
) {
}
