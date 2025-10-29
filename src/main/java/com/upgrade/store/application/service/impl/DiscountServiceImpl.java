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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountMapper discountMapper;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<DiscountResponse> getAllDiscounts() {
        log.debug("[SERVICE] Fetching all discounts");

        List<Discount> discounts = discountRepository.findAll();

        log.info("[SERVICE] Found [{}] total discount(s)", discounts.size());
        return discounts.stream()
                .map(discountMapper::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public DiscountResponse updateDiscount(Long discountId, DiscountRequest discountRequest) {
        log.debug("[SERVICE] Attempting to update discount with ID [{}] using request: {}", discountId, discountRequest);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> {
            log.warn("[SERVICE] Discount with ID [{}] not found", discountId);
            return new EntityNotFoundException("Discount with ID " + discountId + " not found");
        });

        if (discountRequest.productIds() != null) {
            Set<Product> products = productRepository.findAllByIdIn(discountRequest.productIds());
            discount.setProducts(products);
        }

        discountMapper.update(discountRequest, discount);
        Discount updatedDiscount = discountRepository.save(discount);

        log.info("[SERVICE] Updated discount with ID [{}], percentage rate [{}]",
                updatedDiscount.getId(), updatedDiscount.getDiscountPercent());
        return discountMapper.mapToDto(updatedDiscount);
    }

    @Override
    @Transactional
    public DiscountResponse updateDiscountProducts(Long discountId, DiscountProductsUpdateRequest productsUpdateRequest) {
        log.debug("[SERVICE] Updating discount [{}] with products to add: {}, to remove: {}",
                discountId, productsUpdateRequest.productIdsToAdd(), productsUpdateRequest.productIdsToRemove());

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> {
            log.warn("[SERVICE] Discount with ID [{}] not found", discountId);
            return new EntityNotFoundException("Discount with ID " + discountId + " not found");
        });

        Set<Long> toRemove = Optional.ofNullable(productsUpdateRequest.productIdsToRemove()).orElse(Set.of());
        Set<Long> toAdd = Optional.ofNullable(productsUpdateRequest.productIdsToAdd()).orElse(Set.of());

        if (!toRemove.isEmpty()) discount.getProducts().removeIf(product -> toRemove.contains(product.getId()));

        if (!toAdd.isEmpty()) {
            Set<Product> productsToAdd = productRepository.findAllByIdIn(toAdd);
            if (productsToAdd.size() != toAdd.size()) {
                log.warn("[SERVICE] Some products not found while adding to discount [{}]", discountId);
            }
            discount.getProducts().addAll(productsToAdd);
        }

        Discount updatedDiscount = discountRepository.save(discount);

        log.info("[SERVICE] Updated discount [{}]: total products count = {}",
                discountId, updatedDiscount.getProducts().size());
        return discountMapper.mapToDto(updatedDiscount);
    }

    @Override
    @Transactional
    public void deleteDiscount(Long discountId) {
        log.debug("[SERVICE] Attempting to delete discount with ID [{}]", discountId);

        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> {
            log.warn("[SERVICE] Discount with ID [{}] not found for deletion", discountId);
            return new EntityNotFoundException("Discount with ID " + discountId + " not found");
        });

        discountRepository.delete(discount);

        log.info("[SERVICE] Deleted discount with ID [{}] and percentage rate [{}]",
                discount.getId(), discount.getDiscountPercent());
    }
}
