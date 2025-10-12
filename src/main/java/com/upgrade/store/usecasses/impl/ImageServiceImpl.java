package com.upgrade.store.usecasses.impl;

import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.persistence.model.Image;
import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.persistence.repository.ImageRepository;
import com.upgrade.store.persistence.repository.ProductRepository;
import com.upgrade.store.storage.FileStorageService;
import com.upgrade.store.usecasses.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.upgrade.store.usecasses.util.FileUploadManager.uploadFilesWithRollback;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public List<String> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("There is no product with this ID"));
        List<Image> images = uploadFilesWithRollback(product, files, fileStorageService);
        imageRepository.saveAll(images);
        return getUrls(images);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getImageUrlsByProductId(Long productId) {
        return getUrls(imageRepository.findAllByProductId(productId));
    }

    public List<String> getUrls(List<Image> images) {
        return images.stream()
                .map(image -> fileStorageService.getPublicUrl(image.getKey()))
                .collect(Collectors.toList());
    }
}
