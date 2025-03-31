package com.servidor.api.modulos.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Retention;
import io.minio.messages.RetentionMode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioServiceImpl implements MinioService{

  private static final MinioClient minioClient = MinioClient.builder()
          .endpoint("http://localhost:9000")
          .credentials("admin", "admin")
          .build();

  public ObjectWriteResponse uploadFile(byte[] object, String hash) {
    String bucketName = "foto_pessoa";
    try {
            return minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(hash)
                    .stream(new ByteArrayInputStream(object), object.length, -1)
                    .build());
    } catch (Exception e) {
      throw new RuntimeException("Erro ao fazer upload da foto para o Minio", e);
    }
  }

  public String getFileUrl(String hash) {
    String bucketName = "foto_pessoa";
    try {
      Map<String, String> reqParams = new HashMap<String, String>();
      reqParams.put("response-content-type", "application/json");

      return minioClient.getPresignedObjectUrl(
              GetPresignedObjectUrlArgs.builder()
                      .method(Method.GET)
                      .bucket(bucketName)
                      .object(hash)
                      .expiry(5, TimeUnit.MINUTES)
                      .extraQueryParams(reqParams)
                      .build());
    } catch (Exception e) {
      throw new RuntimeException("Erro ao fazer download da foto do Minio", e);
    }
  }

}
