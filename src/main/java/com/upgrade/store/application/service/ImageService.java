package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageResponse> saveImages(Long productId, List<MultipartFile> files);

    List<ImageResponse> getImagesByProductId(Long productId);

    void deleteImage(Long imageId);
}
