package com.upgrade.store.application.provider;

import com.upgrade.store.domain.model.Image;
import com.upgrade.store.domain.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageProvider {

    String getImageUrl(Image image);

    List<Image> uploadImages(Product product, List<MultipartFile> files);

    void deleteImage(String key);
}
