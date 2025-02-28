package com.project.task.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.task.manager.constants.ErrorMessage;
import com.project.task.manager.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {
	private final UserRepository repository;
	
	@Bean
	public UserDetailsService userDetailService () {
		return username -> 
		repository.findByEmail(username).orElseThrow(()-> 
		new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider () {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider ();
		provider.setUserDetailsService(userDetailService ());
		provider.setPasswordEncoder(encoder());
		return provider;
	}
	
	 @Bean
	  public AuthenticationManager authenticationManager(
			  AuthenticationConfiguration config
			  ) throws Exception {
	    return config.getAuthenticationManager();
	  }
	
	@Bean 
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
