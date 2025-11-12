package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;
import com.upgrade.store.domain.model.Category;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CategoryMapper {

    Category mapToEntity(CategoryRequest categoryRequest);

    @Mapping(target = "subCategories", expression = "java(category.getSubCategories().stream().map(this::mapToSubDto).toList())")
    CategoryResponse mapToDto(Category category);

    @Mapping(target = "parentId", source = "parentCategory.id")
    @Mapping(target = "userId", source = "createdByUser.id")
    @Mapping(target = "subCategories", expression = "java(category.getSubCategories().stream().map(this::mapToSubDto).toList())")
    CategoryDetailResponse mapToDetailDto(Category category);

    SubCategoryResponse mapToSubDto(Category category);

    Category update(CategoryRequest categoryRequest, @MappingTarget Category category);
}
