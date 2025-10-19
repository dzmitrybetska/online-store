package com.upgrade.store.infrastructure.storage.impl;

import com.upgrade.store.api.exception.UploadFileException;
import com.upgrade.store.infrastructure.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3FileStorageService implements FileStorageService {

    @Value("${aws.s3.bucket}")
    private String bucket;
    private final S3Client client;

    @Override
    public String uploadFile(Long productId, MultipartFile file) {
        log.debug("[AWS] Attempt to upload a file with name [{}] for a product with ID [{}]",
                file.getOriginalFilename(), productId);

        String key = generateKey(productId, file);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            byte[] bytes = file.getBytes();
            client.putObject(request, RequestBody.fromBytes(bytes));
            log.info("[AWS] Uploaded file [{}] to S3 bucket [{}] -> key={}", file.getOriginalFilename(), bucket, key);
            return key;
        } catch (IOException | SdkException e) {
            log.warn("[AWS] An error occurred while uploading a file with name [{}] for a product with ID [{}]",
                    file.getOriginalFilename(), productId);
            throw new UploadFileException("Failed to upload file: " + file.getOriginalFilename(), e);
        }
    }

    private String generateKey(Long productId, MultipartFile file) {
        return "products/" + productId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }

    @Override
    public String getPublicUrl(String key) {
        log.debug("[AWS] Generate a link for a file with key [{}]", key);

        return "https://" + bucket + ".s3."
                + client.serviceClientConfiguration().region().id()
                + ".amazonaws.com/" + key;
    }

    @Override
    public void deleteFile(String key) {
        log.info("[AWS] Attempt to delete file by key {}", key);
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        client.deleteObject(request);
        log.info("[AWS] The file with key [{}] has been successfully deleted from the bucket [{}]", key, bucket);
    }
}
