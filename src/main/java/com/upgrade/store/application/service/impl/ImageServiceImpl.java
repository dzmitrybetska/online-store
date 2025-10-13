package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.application.service.ImageService;
import com.upgrade.store.domain.model.Image;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.domain.repository.ImageRepository;
import com.upgrade.store.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageProvider imageProvider;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public List<String> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException("There is no product with this ID"));

        List<Image> images = imageProvider.uploadImages(product, files);
        imageRepository.saveAll(images);

        return imageProvider.getImageUrls(images);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getImageUrlsByProductId(Long productId) {
        List<Image> images = imageRepository.findAllByProductId(productId);
        return imageProvider.getImageUrls(images);
    }
}
