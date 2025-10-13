package com.upgrade.store.application.provider.impl;

import com.upgrade.store.application.calculator.SkuGenerator;
import com.upgrade.store.application.provider.SkuProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkuProviderImpl implements SkuProvider {

    private final SkuGenerator skuGenerator;

    public String generateSku(String categoryCode, Long sequence) {
        return skuGenerator.generateSku(categoryCode, sequence);
    }
}
