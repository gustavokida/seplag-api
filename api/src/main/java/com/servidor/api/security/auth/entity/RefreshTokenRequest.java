package com.servidor.api.security.auth.entity;

import lombok.Data;

@Data
public class RefreshTokenRequest {

  private String refreshToken;
}
