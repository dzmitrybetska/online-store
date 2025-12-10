package com.upgrade.store.api.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        UserShortResponse user,
        AccountShortResponse account,
        ProfileShortResponse profile
) {
}
