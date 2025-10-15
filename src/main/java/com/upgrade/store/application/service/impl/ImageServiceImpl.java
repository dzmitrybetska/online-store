package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.assembler.ImageAssembler;
import com.upgrade.store.api.dto.response.ImageResponse;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.application.service.ImageService;
import com.upgrade.store.domain.model.Image;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.domain.repository.ImageRepository;
import com.upgrade.store.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageProvider imageProvider;
    private final ImageAssembler imageAssembler;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<ImageResponse> saveImages(Long productId, List<MultipartFile> files) {
        log.debug("Attempting to upload {} image(s) for product ID [{}]", files.size(), productId);

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> {
                    log.warn("Failed to upload images — product with ID [{}] not found", productId);
                    return new EntityNotFoundException(
                            "Cannot upload images — product with ID " + productId + " not found"
                    );
                });

        List<Image> images = imageProvider.uploadImages(product, files);
        imageRepository.saveAll(images);

        log.info("Uploaded {} image(s) for product ID {}", images.size(), productId);

        return images.stream()
                .map(imageAssembler::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByProductId(Long productId) {
        log.debug("Fetching all images for product ID [{}]", productId);

        List<Image> images = imageRepository.findAllByProductId(productId);

        return images.stream()
                .map(imageAssembler::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        log.debug("Attempting to delete image with ID [{}]", imageId);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    log.warn("Image with ID [{}] not found — deletion aborted", imageId);
                    return new EntityNotFoundException("Image with ID " + imageId + " not found");
                });

        imageProvider.deleteImage(image.getKey());
        imageRepository.delete(image);

        log.info("Deleted image [{}] for product [{}]", image.getId(), image.getProduct().getId());
    }
}
