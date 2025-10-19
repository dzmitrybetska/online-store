package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;
import com.upgrade.store.domain.model.Category;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CategoryMapper {

    Category mapToEntity(CategoryRequest categoryRequest);

    CategoryResponse mapToShortDto(Category category);

    @Mapping(target = "parentId", source = "parentCategory.id")
    @Mapping(target = "userId", source = "createdByUser.id")
    @Mapping(target = "subCategories", expression = "java(category.getSubCategories().stream().map(this::mapToSubDto).toList())")
    CategoryDetailResponse mapToDetailDto(Category category);

    SubCategoryResponse mapToSubDto(Category category);
}
