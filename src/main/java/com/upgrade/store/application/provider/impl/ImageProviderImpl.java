package com.upgrade.store.application.provider.impl;

import com.upgrade.store.api.exception.UploadFileException;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.domain.model.Image;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.infrastructure.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageProviderImpl implements ImageProvider {

    private final FileStorageService fileStorageService;

    @Override
    public String getImageUrl(Image image) {
        return fileStorageService.getPublicUrl(image.getKey());
    }

    @Override
    public List<Image> uploadImages(Product product, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String key = fileStorageService.uploadFile(product.getId(), file);
                Image image = new Image();
                image.setKey(key);
                image.setProduct(product);
                images.add(image);
                log.info("[Image Provider] Uploaded file [{}] for product [{}] -> key={}",
                        file.getOriginalFilename(), product.getId(), key);
            }
        } catch (Exception e) {
            log.error("[Image Provider] Error uploading images for product [{}]. Rolling back...", product.getId(), e);
            rollback(images);
            throw new UploadFileException("Failed to upload images for product ID " + product.getId(), e);
        }
        return images;
    }

    @Override
    public void deleteImage(String key) {
        try {
            fileStorageService.deleteFile(key);
            log.info("[Image Provider] Deleted image with key={}", key);
        } catch (Exception e) {
            log.error("[Image Provider] Failed to delete image with key={}", key, e);
            throw new UploadFileException("Error deleting image with key: " + key, e);
        }
    }

    private void rollback(List<Image> images) {
        for (Image image : images) {
            try {
                fileStorageService.deleteFile(image.getKey());
                log.info("[Image Provider] Rolled back image [{}]", image.getKey());
            } catch (Exception ex) {
                log.warn("[Image Provider] Failed to rollback image [{}]: {}", image.getKey(), ex.getMessage());
            }
        }
    }
}
