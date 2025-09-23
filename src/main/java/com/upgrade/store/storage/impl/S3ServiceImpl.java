package com.upgrade.store.storage.impl;

import com.upgrade.store.api.exception.UploadFileException;
import com.upgrade.store.storage.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket}")
    private String bucket;
    private final S3Client client;

    @Override
    public String uploadFile(String productId, MultipartFile file) {
        String key = "products/" + productId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .contentType(file.getContentType())
                .build();

        try (InputStream is = file.getInputStream()) {
            RequestBody requestBody = RequestBody.fromInputStream(is, file.getSize());
            client.putObject(request, requestBody);
            return key;
        } catch (IOException e) {
            throw new UploadFileException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public String getPublicUrl(String key) {
        return "https://" + bucket + ".s3." + client.serviceClientConfiguration().region().id() + ".amazonaws.com/" + key;
    }

    @Override
    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        client.deleteObject(request);
    }
}
