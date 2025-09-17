package com.upgrade.store.usecasses.impl;

import com.upgrade.store.persistence.model.Product;
import com.upgrade.store.persistence.repository.ProductRepository;
import com.upgrade.store.usecasses.ProductService;
import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import com.upgrade.store.usecasses.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository repository;

    @Override
    public ProductResponse saveProduct(ProductRequest request) {
        Product product = repository.save(mapper.mapToEntity(request));
        return mapper.mapToDto(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> byId = repository.findById(id);

        return null;
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
