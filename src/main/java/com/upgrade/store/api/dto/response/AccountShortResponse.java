package com.upgrade.store.api.dto.response;

public record AccountShortResponse(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
