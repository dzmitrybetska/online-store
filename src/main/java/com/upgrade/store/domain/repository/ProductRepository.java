package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductBySku(String sku);

    List<Product> getProductsByCategory_Id(Long categoryId);

    Set<Product> findAllByIdIn(Set<Long> productIds);
}
