package com.upgrade.store.api.dto.mapper;

import com.upgrade.store.api.dto.response.ImageResponse;
import com.upgrade.store.domain.model.Image;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface ImageMapper {

    @Mapping(target = "url", source = "url")
    ImageResponse mapToDto(Image image, String url);
}
