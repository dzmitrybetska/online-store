package com.upgrade.store.application.provider.impl;

import com.upgrade.store.application.provider.CategoryProvider;
import org.springframework.stereotype.Component;

@Component
public class CategoryProviderImpl implements CategoryProvider {

    @Override
    public String generateCategoryCode(String name) {
        return name.replaceAll("[^A-Za-z]", "").substring(0, 4).toUpperCase();
    }
}
