package com.upgrade.store.persistence.repository;

import com.upgrade.store.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductBySku(String sku);

    Optional<Product> getProductByEan(String ean);

    List<Product> getProductsByCategory_Id(Long categoryId);
}
