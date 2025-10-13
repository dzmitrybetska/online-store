package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductBySku(String sku);

    Optional<Product> getProductByEan(String ean);

    List<Product> getProductsByCategory_Id(Long categoryId);

    boolean existsBySku(String sku);
}
