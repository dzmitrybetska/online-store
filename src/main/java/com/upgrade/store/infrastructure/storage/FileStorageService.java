package com.upgrade.store.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadFile(Long productId, MultipartFile file);

    String getPublicUrl(String key);

    void deleteFile(String key);
}
