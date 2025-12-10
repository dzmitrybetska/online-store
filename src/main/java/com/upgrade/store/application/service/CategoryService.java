package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.CategoryShortResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long categoryId);

    List<CategoryShortResponse> getCategoryTree();

    List<SubCategoryResponse> getSubCategories(Long categoryId);

    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    void deleteCategory(Long categoryId);
}
