package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.DiscountProductsUpdateRequest;
import com.upgrade.store.api.dto.request.DiscountRequest;
import com.upgrade.store.api.dto.response.DiscountResponse;

import java.util.List;

public interface DiscountService {

    DiscountResponse saveDiscount(DiscountRequest discountRequest);

    DiscountResponse getDiscountById(Long discountId);

    List<DiscountResponse> getAllDiscounts();

    DiscountResponse updateDiscount(Long discountId, DiscountRequest discountRequest);

    DiscountResponse updateDiscountProducts(Long discountId, DiscountProductsUpdateRequest productsUpdateRequest);

    void deleteDiscount(Long discountId);
}
