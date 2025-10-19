package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryDetailResponse saveCategory(CategoryRequest request);

    CategoryDetailResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getSubCategories(Long parentId);

    void deleteCategory(Long id);
}
