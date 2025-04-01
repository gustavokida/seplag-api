package com.servidor.api.modulos.minio;

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
  public void init() {
    try {
      boolean bucketExists = minioClient.bucketExists(
              BucketExistsArgs.builder().bucket(bucketName).build()
      );
      if (!bucketExists) {
        minioClient.makeBucket(
                MakeBucketArgs.builder().bucket(bucketName).build()
        );
      }
    } catch (Exception e) {
      throw new RuntimeException("Falha ao iniciar minio bucket", e);
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
