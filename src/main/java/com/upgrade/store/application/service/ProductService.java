package com.upgrade.store.application.service;

import com.upgrade.store.api.dto.request.ProductRequest;
import com.upgrade.store.api.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest request);

    ProductResponse getProductById(Long productId);

    ProductResponse getProductBySku(String sku);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long productId, ProductRequest request);

    void deleteProduct(Long productId);
}
