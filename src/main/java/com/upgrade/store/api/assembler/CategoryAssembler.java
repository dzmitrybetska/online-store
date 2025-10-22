package com.upgrade.store.api.assembler;

import com.upgrade.store.api.dto.mapper.CategoryMapper;
import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;
import com.upgrade.store.application.provider.CategoryProvider;
import com.upgrade.store.domain.model.Category;
import com.upgrade.store.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryAssembler {

    private final CategoryMapper categoryMapper;
    private final CategoryProvider categoryProvider;

    public Category toEntity(CategoryRequest categoryRequest, Category parent, User user) {
        Category category = categoryMapper.mapToEntity(categoryRequest);
        category.setParentCategory(parent);
        category.setCreatedByUser(user);
        String code = categoryProvider.generateCategoryCode(categoryRequest.name());
        category.setCode(code);
        return category;
    }

    public CategoryResponse toShortResponse(Category category) {
        return categoryMapper.mapToDto(category);
    }

    public CategoryDetailResponse toDetailResponse(Category category) {
        return categoryMapper.mapToDetailDto(category);
    }

    public SubCategoryResponse toSubResponse(Category category) {
        return categoryMapper.mapToSubDto(category);
    }
}
