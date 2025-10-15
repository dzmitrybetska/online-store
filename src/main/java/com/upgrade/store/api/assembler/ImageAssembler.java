package com.upgrade.store.api.assembler;

import com.upgrade.store.api.dto.mapper.ImageMapper;
import com.upgrade.store.api.dto.response.ImageResponse;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.domain.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageAssembler {

    private final ImageMapper imageMapper;
    private final ImageProvider imageProvider;

    public ImageResponse toResponse(Image image) {
        String imageUrl = imageProvider.getImageUrl(image);
        return imageMapper.mapToDto(image, imageUrl);
    }
}
