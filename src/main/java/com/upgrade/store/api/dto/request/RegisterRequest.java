package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @Email
        @NotBlank(message = "The field cannot be empty")
        @Schema(defaultValue = "trev23@gmail.com", description = "Enter your email")
        String email,

        @NotBlank(message = "The field cannot be empty")
        @Schema(defaultValue = "gKhkDgl%6793955683ft", description = "Enter your password")
        String password,

        @NotBlank(message = "The field cannot be empty")
        @Schema(defaultValue = "John", description = "Enter your first name")
        String firstName,

        @NotBlank(message = "The field cannot be empty")
        @Schema(defaultValue = "Smith", description = "Enter your last name")
        String lastName,

        @NotBlank(message = "The field cannot be empty")
        @Schema(defaultValue = "+48567874556", description = "Enter your phone number")
        String phoneNumber
) {
}
