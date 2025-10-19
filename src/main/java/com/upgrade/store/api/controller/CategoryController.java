package com.upgrade.store.api.controller;

import com.upgrade.store.api.dto.request.CategoryRequest;
import com.upgrade.store.api.dto.response.CategoryDetailResponse;
import com.upgrade.store.application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "404", description = "Parent category not found")
    })
    public ResponseEntity<CategoryDetailResponse> saveCategory(
            @Parameter(description = "CategoryRequest", required = true)
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        log.info("[API] User with ID [{}] creates a category with name [{}]", categoryRequest.userId(), categoryRequest.name());

        CategoryDetailResponse categoryDetailResponse = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(categoryDetailResponse, HttpStatus.CREATED);
    }
}
