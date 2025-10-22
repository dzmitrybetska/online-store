package com.upgrade.store.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryDetailResponse(
        Long id,
        Long parentId,
        List<SubCategoryResponse> subCategories,
        Long userId,
        LocalDateTime timeOfCreation,
        String name,
        String code,
        Boolean active
) {
}
