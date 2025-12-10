package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.request.DiscountRequest;
import com.upgrade.store.api.dto.response.DiscountResponse;
import com.upgrade.store.domain.model.Discount;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiscountMapper {

    Discount mapToEntity(DiscountRequest discountRequest);

    @Mapping(target = "productIds", expression = "java(discount.getProducts().stream().map(p -> p.getId()).toList())")
    DiscountResponse mapToDto(Discount discount);

    @Mapping(target = "products", ignore = true)
    Discount update(DiscountRequest discountRequest, @MappingTarget Discount discount);
}
