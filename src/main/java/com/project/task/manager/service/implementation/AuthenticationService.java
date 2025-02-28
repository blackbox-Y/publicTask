package com.project.task.manager.service.implementation;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.task.manager.domain.DTO.AuthenticationRequest;
import com.project.task.manager.domain.DTO.AuthenticationResponse;
import com.project.task.manager.domain.DTO.RegisterRequest;
import com.project.task.manager.domain.status.Role;
import com.project.task.manager.repository.UserRepository;
import com.project.task.manager.security.JwtService;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

import com.project.task.manager.domain.User;
import com.project.task.manager.domain.status.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repo;
	private final JwtService jwtService;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.name(request.getName())
				.surnaname(request.getSurname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		repo.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse  authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
						)
				);
		var user = repo.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
