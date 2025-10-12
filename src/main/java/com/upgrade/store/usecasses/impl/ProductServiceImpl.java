package com.upgrade.store.usecasses.impl;

import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.persistence.model.Category;
import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.persistence.repository.CategoryRepository;
import com.upgrade.store.persistence.repository.ProductRepository;
import com.upgrade.store.usecasses.ImageService;
import com.upgrade.store.usecasses.ProductService;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import com.upgrade.store.usecasses.mapper.ProductMapper;
import com.upgrade.store.usecasses.util.DiscountManager;
import com.upgrade.store.usecasses.util.SkuGenerateManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final DiscountManager discountManager;
    private final SkuGenerateManager skuGenerateManager;
    private final ImageService imageService;
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

        return mapper.mapToDto(savedProduct, new ArrayList<>(), savedProduct.getPrice());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no category with this ID"));
        List<String> urls = imageService.getUrls(product.getImages());

        return mapper.mapToDto(
                product,
                urls,
                discountManager.calculatePrice(product)
        );
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
