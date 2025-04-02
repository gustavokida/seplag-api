package com.servidor.api.minio;

import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

  private final MinioClient minioClient;

  @Value("${minio.bucket-name}")
  private String bucketName;

  @PostConstruct
  public void initialize() {
    int maxRetries = 10;
    int delaySeconds = 5;
    for (int attempt = 1; attempt <= maxRetries; attempt++) {
      try {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
          minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        System.out.println("MinIO initialized successfully on attempt " + attempt);
        return;
      } catch (Exception e) {
        if (attempt == maxRetries) {
          throw new RuntimeException("Failed to initialize MinIO after " + maxRetries + " attempts", e);
        }
        System.out.println("MinIO not ready, retrying in " + delaySeconds + " seconds... (Attempt " + attempt + ")");
        try {
          Thread.sleep(delaySeconds * 1000);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

  public ObjectWriteResponse uploadFile(MultipartFile object, String hash) {
    try {
            return minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(hash)
                    .stream(object.getInputStream(), object.getSize(), -1)
                    .contentType(object.getContentType())
                    .build());
    } catch (Exception e) {
      throw new RuntimeException("Erro ao fazer upload da foto para o Minio", e);
    }
  }

  public String getFileUrl(String hash) {
    try {
      return minioClient.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                      .method(Method.GET)
                      .bucket(bucketName)
                      .object(hash)
                      .expiry(5, TimeUnit.MINUTES)
                      .build());
    } catch (Exception e) {
      throw new RuntimeException("Erro ao fazer download da foto do Minio", e);
    }
  }

}
