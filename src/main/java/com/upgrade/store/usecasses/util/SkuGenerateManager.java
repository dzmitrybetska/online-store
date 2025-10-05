package com.upgrade.store.usecasses.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.sku")
public class SkuGenerateManager {

    private int padding;

    public String generateSku(String categoryCode, Long sequence) {
        return categoryCode.toUpperCase() + "-" + String.format("%0" + padding + "d", sequence);
    }
}
