package com.project.task.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.manager.domain.DTO.AuthenticationRequest;
import com.project.task.manager.domain.DTO.AuthenticationResponse;
import com.project.task.manager.domain.DTO.RegisterRequest;
import com.project.task.manager.service.implementation.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "auth_methods")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register (
			@RequestBody RegisterRequest request
			)
	{
		return ResponseEntity.ok(service.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticater (
			@RequestBody AuthenticationRequest request
			)
	{
		return ResponseEntity.ok(service.authenticate(request));
	}
}
