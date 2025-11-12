package com.upgrade.store.application.calculator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.sku")
public class SkuGenerator {

    private int padding;

    public String generateSku(String categoryCode, Long sequence) {
        return categoryCode.toUpperCase() + "-" + String.format("%0" + padding + "d", sequence);
    }
}
