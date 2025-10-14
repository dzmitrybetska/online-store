package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.dto.mapper.ProductMapper;
import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.provider.DiscountProvider;
import com.upgrade.store.application.provider.ImageProvider;
import com.upgrade.store.application.provider.SkuProvider;
import com.upgrade.store.application.service.ProductService;
import com.upgrade.store.domain.model.Category;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.domain.repository.CategoryRepository;
import com.upgrade.store.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final SkuProvider skuProvider;
    private final ImageProvider imageProvider;
    private final DiscountProvider discountProvider;
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
        String sku = skuProvider.generateSku(category.getCode(), sequence);
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
        List<String> urls = product.getImages().stream()
                .map(imageProvider::getImageUrl)
                .toList();

        return mapper.mapToDto(product, urls, discountProvider.calculatePrice(product));
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
