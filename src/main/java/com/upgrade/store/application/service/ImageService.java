package com.upgrade.store.application.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<String> saveImages(Long productId, List<MultipartFile> files);

    List<String> getImageUrlsByProductId(Long productId);
}
