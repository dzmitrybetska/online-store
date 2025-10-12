package com.upgrade.store.usecasses.mapper;

import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product mapToEntity(ProductRequest request);

    @Mapping(target = "productId", expression = "java(product.getId())")
    @Mapping(target = "categoryId", expression = "java(product.getCategory().getId())")
    @Mapping(target = "imageUrls", source = "imageUrls")
    @Mapping(target = "finalPrice", source = "finalPrice")
    @Mapping(target = "discountIDs",
            expression = "java(product.getDiscounts().stream().map(discount -> discount.getId()).toList())")
    ProductResponse mapToDto(Product product, List<String> imageUrls, BigDecimal finalPrice);
}