package com.upgrade.store.api.dto.response;

import java.util.Set;

public record UserShortResponse(
        Long id,
        String email,
        Set<String> roles
) {
}
