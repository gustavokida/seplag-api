package com.servidor.api.security.auth.entity;

import lombok.Data;

@Data
public class AuthRequest {
  private String username;
  private String password;

}