package com.amazkart.jwtcfg;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// Use a secure key for signing the JWT
	private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername()) // Set the subject (username)
				.setIssuedAt(new Date()) // Set the issue time
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set expiration time (10
																							// hours)
				.signWith(SECRET_KEY) // Sign the token with the secure key
				.compact(); // Build the token
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY) // Use the same key for parsing
				.build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY) // Use the same key for validation
					.build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}