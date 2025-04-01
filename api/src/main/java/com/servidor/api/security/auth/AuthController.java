package com.servidor.api.security.auth;

import com.servidor.api.security.auth.entity.AuthRequest;
import com.servidor.api.security.auth.entity.AuthResponse;
import com.servidor.api.security.auth.entity.RefreshTokenRequest;
import com.servidor.api.security.auth.jwt.JwtUtil;
import com.servidor.api.security.auth.entity.RefreshToken;
import com.servidor.api.security.auth.jwt.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;
  private final RefreshTokenService refreshTokenService;

  public AuthController(AuthenticationManager authenticationManager,
                        UserDetailsService userDetailsService,
                        JwtUtil jwtUtil,
                        RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtUtil = jwtUtil;
    this.refreshTokenService = refreshTokenService;
  }

  @Transactional
  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Incorrect username or password");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    final String accessToken = jwtUtil.generateAccessToken(userDetails);
    final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

    return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
  }

  @Transactional
  @PostMapping("/refresh")
  public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshRequest) {
    String refreshToken = refreshRequest.getRefreshToken();

    return refreshTokenService.findByToken(refreshToken)
            .filter(token -> token.getExpiryDate().isAfter(Instant.now()))
            .map(token -> {
              UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUser().getUsername());
              String newAccessToken = jwtUtil.generateAccessToken(userDetails);
              return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
            })
            .orElseGet(() -> ResponseEntity.status(401).build());
  }

}