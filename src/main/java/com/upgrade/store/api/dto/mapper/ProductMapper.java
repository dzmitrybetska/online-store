package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.domain.model.Product;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    Product mapToEntity(ProductRequest request);

    @Mapping(target = "productId", expression = "java(product.getId())")
    @Mapping(target = "categoryId", expression = "java(product.getCategory().getId())")
    @Mapping(target = "discountIDs",
            expression = "java(product.getDiscounts().stream().map(discount -> discount.getId()).toList())")
    ProductResponse mapToDto(Product product, List<String> imageUrls, BigDecimal finalPrice);

    Product update(ProductRequest request, @MappingTarget Product product);
}