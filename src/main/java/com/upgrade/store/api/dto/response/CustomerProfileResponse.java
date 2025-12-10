package com.upgrade.store.api.dto.response;

import java.util.List;

public record CustomerProfileResponse(
        Long id,
        AccountShortResponse userAccount,
        List<DeliveryAddressResponse> addresses,
        List<Long> orderIds,
        Integer loyaltyPoints
) {
}
