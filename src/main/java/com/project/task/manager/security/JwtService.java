package com.project.task.manager.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
	
	private final static String SECRET_KEY = 
			"DdLYcZm6GaU47bMjSbmfpaYjR5x5dRC3fX/RmXGgcNrzUdvJFAIKv0vV8rGR/ExeIXbAD6hRiG+VIeGOiQnkLz5SpS5FQqqK66JEsYetZ5Ip7BAbo3rGdyPNumSET/3Axxit7auBUnb2Wmx5oi8pMcMjGAs4Toju4B/O64dHnqCS3VBsEtOfm7cEkEiFlcOkjFxhixy3K7VqDXsYQu5fs2inV4QkpHjLPsYUM1vpYFCzBSlqalFk8bYdZONEK2molyLhaciQvl75d7HiF1WwForSF5SEUmbpTNXkHq/TlHDkAipfhY8RQ2/eii7FGEXiDqZixdUOBRf+a+f4277TXyWqEbrvm6thmALr4iLnFps=\n"; 

	public String extractUserName(String token) {
		return extractClaim (token, Claims::getSubject);
	}
	 
	public <T> T extractClaim (String token, Function<Claims, T> ClaimsResolver) {
		final Claims claims = extractAllClaims(token);
		return ClaimsResolver.apply(claims);
	}
	
	public String generateToken (UserDetails userDetails) {
		return generateToken(new HashMap<> (), userDetails);
	}
	
	public String generateToken (Map <String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24830))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact() ;
	}
	
	
	public boolean isTokenValid (String token, UserDetails userDetails) {
		final String userEmail = extractUserName(token);
		return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
	}

	private Key getSignInKey() {
		byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBites);
	}

}
