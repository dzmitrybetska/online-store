package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.dto.mapper.DiscountMapper;
import com.upgrade.store.api.dto.request.DiscountProductsUpdateRequest;
import com.upgrade.store.api.dto.request.DiscountRequest;
import com.upgrade.store.api.dto.response.DiscountResponse;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.service.DiscountService;
import com.upgrade.store.domain.model.Discount;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.domain.repository.DiscountRepository;
import com.upgrade.store.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountMapper discountMapper;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    @Override
    public DiscountResponse saveDiscount(DiscountRequest discountRequest) {
        log.debug("[SERVICE] Attempting to save new [{}] percent discount", discountRequest.discountPercent());

        Discount discount = discountMapper.mapToEntity(discountRequest);

        if (discountRequest.productIds() != null && !discountRequest.productIds().isEmpty()) {
            Set<Product> products = productRepository.findAllByIdIn(discountRequest.productIds());
            discount.setProducts(products);
        }

        Discount savedDiscount = discountRepository.save(discount);

        log.info("[SERVICE] Created new discount with ID [{}], valid from [{}] to [{}]",
                savedDiscount.getId(), savedDiscount.getDiscountStart(), savedDiscount.getDiscountEnd());
        return discountMapper.mapToDto(savedDiscount);
    }

    @Override
    public DiscountResponse getDiscountById(Long discountId) {
        log.debug("[SERVICE] Fetching discount by ID [{}]", discountId);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> {
            log.warn("[SERVICE] Discount with ID [{}] not found", discountId);
            return new EntityNotFoundException("Discount with ID " + discountId + " not found");
        });

        log.info("[SERVICE] Discount with ID [{}] and percentage rate [{}] was successfully found",
                discount.getId(), discount.getDiscountPercent());
        return discountMapper.mapToDto(discount);
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        log.debug("[SERVICE] Fetching all discounts");

        List<Discount> discounts = discountRepository.findAll();

        log.info("[SERVICE] Found [{}] total discount(s)", discounts.size());
        return discounts.stream()
                .map(discountMapper::mapToDto)
                .toList();
    }

    @Override
    public DiscountResponse updateDiscount(Long discountId, DiscountRequest discountRequest) {
        return null;
    }

    @Override
    public DiscountResponse updateDiscountProducts(Long discountId, DiscountProductsUpdateRequest productsUpdateRequest) {
        return null;
    }

    @Override
    public void deleteDiscount(Long discountId) {
        log.debug("[SERVICE] Attempting to delete discount with ID [{}]", discountId);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> {
            log.warn("[SERVICE] Discount with ID [{}] not found for deletion", discountId);
            return new EntityNotFoundException("Discount with ID " + discountId + " not found");
        });

        discountRepository.delete(discount);

        log.info("[SERVICE] Deleted discount with ID [{}], percentage rate [{}], start date [{}] and end date [{}]",
                discount.getId(), discount.getDiscountPercent(), discount.getDiscountStart(), discount.getDiscountEnd());
    }
}
