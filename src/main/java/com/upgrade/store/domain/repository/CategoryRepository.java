package com.upgrade.store.domain.repository;

import com.upgrade.store.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT nextval('category_sku_seq')", nativeQuery = true)
    Long getNextSkuSequence();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.parentCategory IS NULL")
    List<Category> getAllRootCategories();
}
