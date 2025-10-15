package com.upgrade.store.api.assembler;

import com.upgrade.store.api.dto.mapper.ProductMapper;
import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.application.provider.DiscountProvider;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.domain.model.Category;
import com.upgrade.store.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAssembler {

    private final ProductMapper productMapper;
    private final ImageProvider imageProvider;
    private final DiscountProvider discountProvider;

    public Product toEntity(ProductRequest productRequest, Category category, String sku) {
        Product product = productMapper.mapToEntity(productRequest);
        product.setCategory(category);
        product.setSku(sku);
        return product;
    }

    public Product update(ProductRequest productRequest, Product product) {
        return productMapper.update(productRequest, product);
    }

    public ProductResponse toResponse(Product product) {
        List<String> urls = product.getImages().stream()
                .map(imageProvider::getImageUrl)
                .toList();
        BigDecimal finalPrice = discountProvider.calculatePrice(product);
        return productMapper.mapToDto(product, urls, finalPrice);
    }
}
