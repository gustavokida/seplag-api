package com.servidor.api.modulos.minio;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

  ObjectWriteResponse uploadFile(MultipartFile object, String fileName);

  String getFileUrl(String fileName);
}
