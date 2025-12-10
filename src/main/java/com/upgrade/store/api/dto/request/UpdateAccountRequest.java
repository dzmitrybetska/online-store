package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateAccountRequest(
        @NotBlank(message = "The field cannot be empty")
        @Length(max = 100, message = "The number of characters is no more than 100")
        @Schema(defaultValue = "John", description = "Enter your first name")
        String firstName,

        @NotBlank(message = "The field cannot be empty")
        @Length(max = 100, message = "The number of characters is no more than 100")
        @Schema(defaultValue = "Smith", description = "Enter your last name")
        String lastName,

        @NotBlank(message = "The field cannot be empty")
        @Length(max = 50, message = "The number of characters is no more than 50")
        @Schema(defaultValue = "+48567874556", description = "Enter your phone number")
        String phoneNumber
) {
}
