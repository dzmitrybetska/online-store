package com.upgrade.store.usecasses.util;

import com.upgrade.store.api.exception.UploadFileException;
import com.upgrade.store.persistence.model.Image;
import com.upgrade.store.storage.FileStorageService;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class FileUploadManager {

    public static List<Image> uploadFilesWithRollback(
            Long entityId,
            List<MultipartFile> files,
            FileStorageService storageService
    ) {
        List<Image> images = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String key = storageService.uploadFile(entityId, file);
                images.add(new Image(key));
                log.info("Uploaded file [{}] for entity [{}] -> key={}",
                        file.getOriginalFilename(), entityId, key);
            }
            return images;
        } catch (RuntimeException e) {
            log.error("Error uploading files for entity [{}]. Rolling back uploaded images...", entityId, e);
            rollback(images, entityId, storageService);
            throw new UploadFileException("Failed to upload files for entity " + entityId, e);
        }
    }

    private static void rollback(List<Image> images, Long entityId, FileStorageService storageService) {
        for (Image img : images) {
            try {
                storageService.deleteFile(img.getKey());
                log.info("Rolled back image [{}] for entity [{}]", img.getKey(), entityId);
            } catch (Exception ex) {
                log.warn("Failed to delete image [{}] during rollback for entity [{}]: {}",
                        img.getKey(), entityId, ex.getMessage(), ex);
            }
        }
    }
}
