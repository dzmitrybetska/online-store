package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.api.dto.response.CategoryResponse;
import com.upgrade.store.api.dto.response.SubCategoryResponse;
import com.upgrade.store.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${app.api.uriPrefix}/category")
@Tag(name = "Category Controller", description = "API for working with categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Save new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category successfully saved",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CategoryDetailResponse> saveCategory(
            @Parameter(description = "CategoryRequest", required = true)
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        log.info("[API] User with ID [{}] creates a category with name [{}]", categoryRequest.userId(), categoryRequest.name());

        CategoryDetailResponse detailResponse = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(detailResponse, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/{categoryId}",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get category by ID", description = "Returns detailed information about a specific category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryDetailResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid category ID"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDetailResponse> getCategoryById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long categoryId) {
        log.info("[API] Fetching category by ID [{}]", categoryId);

        CategoryDetailResponse detailResponse = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(detailResponse, HttpStatus.OK);
    }

    @GetMapping(
            value = "/tree",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get full category tree",
            description = "Returns all top-level categories with their subcategories (one level deep)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subcategories retrieved successfully",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
    })
    public ResponseEntity<List<CategoryResponse>> getCategoryTree() {
        log.info("[API] Fetching category tree");

        List<CategoryResponse> categoryTree = categoryService.getCategoryTree();
        return new ResponseEntity<>(categoryTree, HttpStatus.OK);
    }

    @GetMapping(
            value = "/{categoryId}/subcategories",
            produces = APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get subcategories", description = "Returns subcategories for the specified category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SubCategoryResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid parent category ID"),
    })
    public ResponseEntity<List<SubCategoryResponse>> getSubCategories(
            @Parameter(description = "Parent category ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long categoryId
    ) {
        log.info("[API] Fetching category by parent ID [{}]", categoryId);

        List<SubCategoryResponse> subCategories = categoryService.getSubCategories(categoryId);
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete category by ID", description = "Deletes an category by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category ID"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable @NotNull(message = "Required field") Long categoryId
    ) {
        log.info("[API] Deleting category by ID: {}", categoryId);

        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
