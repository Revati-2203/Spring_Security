package com.project.springSecurity.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.springSecurity.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secretKey}")
	private String jwtSecreteKey;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getId().toString())
				.claim("email", user.getEmail())
				.claim("roles", Set.of("ADMIN","USER"))
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+1000*60))
				.signWith(getSecretKey())
				.compact();		
	}
	
	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return Long.valueOf(claims.getSubject());
	}
	
	
}
