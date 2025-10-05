package com.upgrade.store.usecasses.impl;

import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.persistence.model.Category;
import com.upgrade.store.persistence.model.Image;
import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.persistence.repository.CategoryRepository;
import com.upgrade.store.persistence.repository.ProductRepository;
import com.upgrade.store.storage.FileStorageService;
import com.upgrade.store.usecasses.ProductService;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import com.upgrade.store.usecasses.mapper.ProductMapper;
import com.upgrade.store.usecasses.util.SkuGenerateManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.upgrade.store.usecasses.util.FileUploadManager.uploadFilesWithRollback;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final SkuGenerateManager skuGenerateManager;
    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = mapper.mapToEntity(request);

        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("There is no category with this ID"));

        Long sequence = categoryRepository.getNextSkuSequence();
        String sku = skuGenerateManager.generateSku(category.getCode(), sequence);
        product.setSku(sku);
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        List<Image> images = uploadFilesWithRollback(savedProduct.getId(), request.files(), fileStorageService);
        savedProduct.setImages(images);

        List<String> imageUrls = getUrls(images);
        return mapper.mapToDto(product, imageUrls);
    }

    private List<String> getUrls(List<Image> images) {
        return images.stream()
                .map(image -> fileStorageService.getPublicUrl(image.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id).map(product -> mapper.mapToDto(product, getUrls(product.getImages())))
                .orElseThrow(() -> new EntityNotFoundException("There is no category with this ID"));
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        return null;
    }

    @Override
    public ProductResponse getProductByEan(String ean) {
        return null;
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return null;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return null;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
