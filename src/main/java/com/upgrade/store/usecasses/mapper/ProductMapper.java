package com.upgrade.store.usecasses.mapper;

import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface ProductMapper {

    Product mapToEntity(ProductRequest request);

    ProductResponse mapToDto(Product product);
}
