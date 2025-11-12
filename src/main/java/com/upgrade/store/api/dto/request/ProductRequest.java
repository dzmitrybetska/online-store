package com.upgrade.store.api.dto.request;

import com.upgrade.store.domain.model.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "The field cannot be empty")
        @Length(max = 150, message = "No more than 150 characters")
        @Schema(defaultValue = "Laptop Lenovo", description = "Enter the product name")
        String name,

        @Schema(description = "Enter a product description")
        String description,

        @NotNull(message = "Required field")
        @Schema(defaultValue = "250", description = "Enter the price of the product")
        BigDecimal price,

        @NotNull(message = "Required field")
        @Schema(defaultValue = "23", description = "Enter the quantity of the product")
        Integer quantityInStock,

        @Pattern(regexp = "\\d+", message = "EAN must contain only digits")
        @Length(min = 8, max = 13, message = "EAN cannot contain less than 8 or more than 13 digits.")
        @Schema(defaultValue = "3423445422123", description = "Enter the product's ean")
        String ean,

        @NotNull(message = "Required field")
        @Schema(description = "Enter category ID")
        Long categoryId,

        @NotNull(message = "Required field")
        @Schema(defaultValue = "ACTIVE", description = "Enter the product status")
        ProductStatus status
) implements Serializable {
}
