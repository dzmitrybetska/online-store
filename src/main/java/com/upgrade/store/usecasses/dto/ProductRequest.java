package com.upgrade.store.usecasses.dto;

import com.upgrade.store.persistence.model.Category;
import com.upgrade.store.persistence.model.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        @NotEmpty(message = "Required field")
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

        String ean,

        Long categoryId,

        @Size(message = "The maximum number of photos should not exceed 25")
        @Schema(type = "string", format = "binary")
        List<MultipartFile> files,

        Boolean active
) implements Serializable {
}
