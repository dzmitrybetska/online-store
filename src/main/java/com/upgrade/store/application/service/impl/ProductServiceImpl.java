package com.upgrade.store.application.service.impl;

import com.upgrade.store.api.assembler.ProductAssembler;
import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;
import com.upgrade.store.api.exception.EntityNotFoundException;
import com.upgrade.store.application.provider.SkuProvider;
import com.upgrade.store.application.service.ProductService;
import com.upgrade.store.domain.model.Category;
import com.upgrade.store.domain.model.Product;
import com.upgrade.store.domain.repository.CategoryRepository;
import com.upgrade.store.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final SkuProvider skuProvider;
    private final ProductAssembler productAssembler;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductResponse saveProduct(ProductRequest request) {
        log.debug("Attempting to save new product: {}", request);

        Long categoryId = request.categoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category with ID [{}] not found while creating product", categoryId);
                    return new EntityNotFoundException("Category with ID " + categoryId + " not found");
                });

        Long sequence = categoryRepository.getNextSkuSequence();
        String sku = skuProvider.generateSku(category.getCode(), sequence);
        log.debug("Generated SKU [{}] for category [{}]", sku, category.getCode());

        Product product = productAssembler.toEntity(request, category, sku);
        Product savedProduct = productRepository.save(product);

        log.info("Created new product with ID [{}], SKU [{}]", savedProduct.getId(), savedProduct.getSku());
        return productAssembler.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) {
        log.debug("Fetching product by ID [{}]", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product with ID [{}] not found", productId);
                    return new EntityNotFoundException("Product with ID " + productId + " not found");
                });

        log.debug("Found product with ID [{}], name [{}]", product.getId(), product.getName());
        return productAssembler.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductBySku(String sku) {
        log.debug("Fetching product by SKU [{}]", sku);

        Product product = productRepository.getProductBySku(sku)
                .orElseThrow(() -> {
                    log.warn("Product with SKU [{}] not found", sku);
                    return new EntityNotFoundException("Product with SKU " + sku + " not found");
                });

        log.debug("Found product with SKU [{}], ID [{}]", sku, product.getId());
        return productAssembler.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        log.debug("Fetching products for category ID [{}]", categoryId);

        List<Product> products = productRepository.getProductsByCategory_Id(categoryId);
        log.info("Found [{}] product(s) for category ID [{}]", products.size(), categoryId);

        return products.stream()
                .map(productAssembler::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        log.debug("Fetching all products");

        List<Product> products = productRepository.findAll();
        log.info("Found [{}] total product(s)", products.size());

        return products.stream()
                .map(productAssembler::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        log.debug("Attempting to update product with ID [{}] using request: {}", productId, productRequest);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product with ID [{}] not found for update", productId);
                    return new EntityNotFoundException("Product with ID " + productId + " not found");
                });

        Long categoryId = productRequest.categoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category with ID [{}] not found during product update", categoryId);
                    return new EntityNotFoundException("Category with ID " + categoryId + " not found");
                });

        product.setCategory(category);
        Product updatedProduct = productAssembler.update(productRequest, product);
        productRepository.save(updatedProduct);

        log.info("Updated product with ID [{}], name [{}]", updatedProduct.getId(), updatedProduct.getName());
        return productAssembler.toResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        log.debug("Attempting to delete product with ID [{}]", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product with ID [{}] not found for deletion", productId);
                    return new EntityNotFoundException("Product with ID " + productId + " not found");
                });

        productRepository.delete(product);
        log.info("Deleted product with ID [{}], name [{}]", product.getId(), product.getName());
    }
}
