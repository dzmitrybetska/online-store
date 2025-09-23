package com.upgrade.store.storage;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadFile(String productId, MultipartFile file);

    String getPublicUrl(String key);

    void deleteFile(String key);
}
