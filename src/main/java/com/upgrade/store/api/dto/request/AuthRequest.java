package com.upgrade.store.api.dto.request;

public record AuthRequest(
        String email,
        String password
) {
}
