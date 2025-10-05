package com.upgrade.store.usecasses;

import com.upgrade.store.usecasses.dto.ProductRequest;
import com.upgrade.store.usecasses.dto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse getProductBySku(String sku);

    ProductResponse getProductByEan(String ean);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
