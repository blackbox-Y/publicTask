package com.project.task.manager.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.task.manager.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final UserRepository repository;
	
	private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
	
	
	
	
	public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> 
						auth.requestMatchers("api/v1/task/**" , "api/v1/demo/**")
						.permitAll()
						.anyRequest()
						.authenticated()
						)
				.sessionManagement(
						session -> 
						session
						.sessionCreationPolicy(
								SessionCreationPolicy.STATELESS
								)
						)
				.authenticationProvider(
						authenticationProvider
						) 
				.addFilterBefore(
						jwtAuthFilter, 
						UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	

	
	

}
