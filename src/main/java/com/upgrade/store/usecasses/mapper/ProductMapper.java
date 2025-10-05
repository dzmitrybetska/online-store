package com.upgrade.store.usecasses.mapper;

import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product mapToEntity(ProductRequest request);

    @Mapping(target = "imageUrls", source = "imageUrls")
    ProductResponse mapToDto(Product product, List<String> imageUrls);
}