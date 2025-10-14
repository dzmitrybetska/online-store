package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.dto.mapper.ImageMapper;
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

    private final ImageMapper imageMapper;
    private final ImageProvider imageProvider;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<ImageResponse> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot upload images — product with ID " + productId + " not found"));

        List<Image> images = imageProvider.uploadImages(product, files);
        imageRepository.saveAll(images);

        log.info("Uploaded {} image(s) for product ID {}", images.size(), productId);

        return images.stream()
                .map(image -> imageMapper.mapToDto(image, imageProvider.getImageUrl(image)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageResponse> getImagesByProductId(Long productId) {
        List<Image> images = imageRepository.findAllByProductId(productId);

        return images.stream()
                .map(image -> imageMapper.mapToDto(image, imageProvider.getImageUrl(image)))
                .toList();
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image with ID " + imageId + " not found"));

        imageProvider.deleteImage(image.getKey());

        imageRepository.delete(image);
        log.info("Deleted image [{}] for product [{}]", image.getId(), image.getProduct().getId());
    }
}
