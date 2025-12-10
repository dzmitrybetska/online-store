package com.upgrade.store.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DeliveryAddressRequest(
        @NotBlank(message = "The field cannot be empty")
        @Length(max = 6, message = "The number of characters is no more than 6")
        @Schema(defaultValue = "345-23", description = "Enter zipcode")
        String zipcode,

        @NotBlank(message = "The field cannot be empty")
        @Length(max = 30, message = "The number of characters is no more than 30")
        @Schema(defaultValue = "Poznan", description = "Enter your city")
        String city,

        @NotBlank(message = "The field cannot be empty")
        @Length(max = 100, message = "The number of characters is no more than 100")
        @Schema(defaultValue = "Polabska", description = "Enter your street")
        String street,

        @NotBlank(message = "The field cannot be empty")
        @Length(max = 7, message = "The number of characters is no more than 7")
        @Schema(defaultValue = "334", description = "Enter your house number")
        String houseNumber,

        @Schema(defaultValue = "35", description = "Enter your flat number")
        String flatNumber
) {
}
