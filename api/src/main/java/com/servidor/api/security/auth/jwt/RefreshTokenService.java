package com.servidor.api.security.auth.jwt;

import com.servidor.api.security.auth.entity.RefreshToken;
import com.servidor.api.security.auth.user.User;
import com.servidor.api.security.auth.user.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtUtil jwtUtil;
  private final UserServiceImpl userService;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil, UserServiceImpl userService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  public RefreshToken createRefreshToken(String username) {
    RefreshToken refreshToken = new RefreshToken();
    User user = (User) userService.loadUserByUsername(username);
    refreshToken.setUser(user);
    refreshToken.setToken(jwtUtil.generateRefreshTokenString());
    refreshToken.setExpiryDate(jwtUtil.calculateRefreshTokenExpiry());
    refreshTokenRepository.deleteByUserId(user.getId());
    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public boolean validateRefreshToken(String token) {
    Optional<RefreshToken> refreshToken = findByToken(token);
    return refreshToken.isPresent() && !refreshToken.get().getExpiryDate().isBefore(Instant.now());
  }

  public void deleteRefreshToken(String token) {
    refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
  }
}