package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.assembler.CategoryAssembler;
import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.service.CategoryService;
import com.upgrade.store.domain.model.Category;
import com.upgrade.store.domain.model.User;
import com.upgrade.store.domain.repository.CategoryRepository;
import com.upgrade.store.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAssembler categoryAssembler;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDetailResponse saveCategory(CategoryRequest categoryRequest) {
        Long userId = categoryRequest.userId();
        Long parentId = categoryRequest.parentId();

        log.debug("[SERVICE] User with ID [{}] creates a category with name [{}]", userId, categoryRequest.name());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("[SERVICE] User with ID [{}] not found", userId);
                    return new EntityNotFoundException("User with ID " + userId + " not found");
                });

        Category parentCategory = checkParentCategory(parentId);

        Category category = categoryAssembler.toEntity(categoryRequest, parentCategory, user);
        Category savedCategory = categoryRepository.save(category);

        log.info("[SERVICE] The category with ID [{}] and name [{}] was saved successfully",
                savedCategory.getId(), savedCategory.getName());
        return categoryAssembler.toDetailResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDetailResponse getCategoryById(Long categoryId) {
        log.debug("[SERVICE] Fetching category by ID [{}]", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("[SERVICE] Category with ID [{}] not found", categoryId);
                    return new EntityNotFoundException("Category with ID " + categoryId + " not found");
                });

        log.info("[SERVICE] Category with ID [{}] and name [{}] was found successfully", category.getId(), category.getName());
        return categoryAssembler.toDetailResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        log.debug("[SERVICE] Fetching category tree");

        List<Category> rootCategories = categoryRepository.getAllRootCategories();

        log.info("[SERVICE] [{}] root categories found", rootCategories.size());
        return rootCategories.stream()
                .map(categoryAssembler::toShortResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubCategoryResponse> getSubCategories(Long categoryId) {
        log.debug("[SERVICE] Fetching subcategories by parent category id [{}]", categoryId);

        List<Category> subCategories = categoryRepository.getCategoriesByParentCategoryId(categoryId);

        log.info("[SERVICE] Found [{}] total categories", subCategories.size());
        return subCategories.stream()
                .map(categoryAssembler::toSubResponse)
                .toList();
    }

    @Override
    @Transactional
    public CategoryDetailResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Long parentId = categoryRequest.parentId();

        log.debug("[Service] Attempting to update category with ID [{}] using request: {}", categoryId, categoryRequest);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("[SERVICE] Category with ID [{}] not found when updating category", categoryId);
                    return new EntityNotFoundException("Category with ID " + categoryId + " not found");
                });

        Category parentCategory = checkParentCategory(parentId);

        categoryAssembler.updateCategory(categoryRequest, category, parentCategory);
        Category updatedCategory = categoryRepository.save(category);

        log.debug("[SERVICE] Saving updated category entity: {}", updatedCategory);
        log.info("[SERVICE] Updated category with ID [{}], name [{}]", updatedCategory.getId(), updatedCategory.getName());
        return categoryAssembler.toDetailResponse(updatedCategory);
    }

    private Category checkParentCategory(Long parentId) {
        Category parentCategory = null;
        if (parentId != null) {
            parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> {
                        log.warn("[SERVICE] Parent category with ID [{}] not found", parentId);
                        return new EntityNotFoundException("Parent category with ID " + parentId + " not found");
                    });
        }
        return parentCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        log.debug("[SERVICE] Attempting to delete category with ID [{}]", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("[SERVICE] Category with ID [{}] not found", categoryId);
                    return new EntityNotFoundException("Category with ID " + categoryId + " not found");
                });

        categoryRepository.delete(category);

        log.info("[SERVICE] Deleted category with ID [{}], name [{}]", category.getId(), category.getName());
    }
}
