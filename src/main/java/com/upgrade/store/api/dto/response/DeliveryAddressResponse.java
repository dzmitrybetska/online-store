package com.upgrade.store.api.dto.response;

public record DeliveryAddressResponse(
        Long id,
        String zipcode,
        String city,
        String street,
        String houseNumber,
        String flatNumber
) {
}
