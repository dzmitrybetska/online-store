package com.upgrade.store.usecasses;

import com.upgrade.store.persistence.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<String> saveImages(Long productId, List<MultipartFile> files);

    List<String> getImageUrlsByProductId(Long productId);

    List<String> getUrls(List<Image> images);
}
