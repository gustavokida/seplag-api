package com.servidor.api.modulos.minio;

import io.minio.GetObjectResponse;
import io.minio.ObjectWriteResponse;

public interface MinioService {

  ObjectWriteResponse uploadFile(byte[] object, String fileName);

  String getFileUrl(String fileName);
}
