package com.servidor.api.security.auth.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  public void createInitialUsers() {
    if (userRepository.findByUsername("admin").isEmpty()) {
      User admin = new User();
      Set<String> adminRoles = new HashSet<>();
      adminRoles.add("ADMIN");
      adminRoles.add("USER");
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin123"));
      admin.setRoles(adminRoles);
      userRepository.save(admin);
    }

    if (userRepository.findByUsername("user").isEmpty()) {
      User user = new User();
      Set<String> userRoles = new HashSet<>();
      userRoles.add("USER");
      user.setUsername("user");
      user.setPassword(passwordEncoder.encode("user123"));
      user.setRoles(userRoles);
      userRepository.save(user);
    }
  }
}