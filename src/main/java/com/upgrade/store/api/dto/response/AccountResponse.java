package com.upgrade.store.api.dto.response;

public record AccountResponse(
        Long id,
        UserShortResponse userShortResponse,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
