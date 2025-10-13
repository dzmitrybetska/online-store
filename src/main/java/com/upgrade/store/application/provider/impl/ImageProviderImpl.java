package com.upgrade.store.application.provider.impl;

import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.application.util.FileUploadHelper;
import com.upgrade.store.domain.model.Image;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.infrastructure.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ImageProviderImpl implements ImageProvider {

    private final FileStorageService fileStorageService;

    @Override
    public List<String> getImageUrls(List<Image> images) {
        return images.stream()
                .map(image -> fileStorageService.getPublicUrl(image.getKey()))
                .collect(Collectors.toList());
    }

    public List<Image> uploadImages(Product product, List<MultipartFile> files) {
        return FileUploadHelper.uploadFilesWithRollback(product, files, fileStorageService);
    }
}
