package com.upgrade.store.api.dto.response;

public record CategoryResponse(
        Long id,
        String name,
        Boolean active,
        Long parentCategoryId
) {
}
