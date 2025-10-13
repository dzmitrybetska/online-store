package com.upgrade.store.application.provider;

public interface SkuProvider {

    String generateSku(String categoryCode, Long sequence);
}
