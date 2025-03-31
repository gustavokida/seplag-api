package com.servidor.api.security.auth.entity;

import com.servidor.api.security.auth.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "refresh_token_id")
  private Long id;

  @Column(name = "token", nullable = false, unique = true)
  private String token;

  @Column(name ="expiry_date",nullable = false)
  private Instant expiryDate;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public RefreshToken(String token, Instant expiryDate, User user) {
    this.token = token;
    this.expiryDate = expiryDate;
    this.user = user;
  }

}