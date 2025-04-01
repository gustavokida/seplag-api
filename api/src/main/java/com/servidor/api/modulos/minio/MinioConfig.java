package com.servidor.api.modulos.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MinioConfig {

  @Value("${minio.url}")
  private String url;

  @Value("${minio.access-key}")
  private String accessKey;

  @Value("${minio.secret-key}")
  private String secretKey;

  @Value("${minio.bucket-name}")
  private String bucketName;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
            .endpoint(url)
            .credentials(accessKey, secretKey)
            .build();
  }
}