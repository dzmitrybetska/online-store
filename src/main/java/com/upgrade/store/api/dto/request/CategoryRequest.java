package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Required field")
        @Schema(defaultValue = "1", description = "Enter the parent category ID")
        Long parentId,

        @NotNull(message = "Required field")
        @Schema(defaultValue = "1", description = "Enter the ID of the user creating the category")
        Long userId,

        @NotBlank(message = "The field cannot be empty")
        @Schema(description = "Enter the product name")
        String name,

        @NotNull(message = "Required field")
        @Schema(defaultValue = "true", description = "Please enter whether the category is active or not: true or false")
        Boolean active
) {
}
