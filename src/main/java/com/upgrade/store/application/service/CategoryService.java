package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailResponse saveCategory(CategoryRequest request);

    CategoryDetailResponse getCategoryById(Long categoryId);

    List<CategoryResponse> getCategoryTree();

    List<SubCategoryResponse> getSubCategories(Long categoryId);

    void deleteCategory(Long categoryId);
}
