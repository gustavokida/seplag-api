package com.servidor.api;

import com.servidor.api.security.auth.user.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
public class ApiApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ApiApplication.class, args);
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		userService.createInitialUsers();
	}

}
