package com.amazkart.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKeyString;

	@Value("${jwt.expiration}")
	private long jwtExpirationInMillis;

	private SecretKey SECRET_KEY;

	@PostConstruct
	public void init() {
		this.SECRET_KEY = Keys.hmacShaKeyFor(secretKeyString.getBytes());
	}

	// Generate JWT Token with Claims
	public String generateToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList()));
		claims.put("userId", userDetails.getUserId());
		return generateTokenWithClaims(claims, userDetails.getUsername());
	}

	// Generalized Token Generator to support additional claims
	public String generateTokenWithClaims(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis)).signWith(SECRET_KEY)
				.compact();
	}

	// Extract Username from Token
	public String extractUsername(String token) {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException("Token cannot be null or empty");
		}

		Claims claims = getClaimsFromToken(token).getBody();
		if (claims == null || claims.get("userId") == null) {
			throw new IllegalArgumentException("UserId is missing in the token");
		}
		return getClaimsFromToken(token).getBody().getSubject();
	}

	// Extract UserId from Token
	public Long extractUserId(String token) {
		if (token == null || token.isEmpty()) {
			throw new IllegalArgumentException("Token cannot be null or empty");
		}

		Claims claims = getClaimsFromToken(token).getBody();
		if (claims == null || claims.get("userId") == null) {
			throw new IllegalArgumentException("UserId is missing in the token");
		}

		return claims.get("userId", Long.class); // Extract and return userId
	}

	// Extract Roles from Token
	public List<String> extractRoles(String token) {
		Claims claims = getClaimsFromToken(token).getBody();
		return claims.get("roles", List.class);
	}

	// Validate Token with SLF4J Logging
	public boolean validateToken(String token) {
		try {
			getClaimsFromToken(token); // If no exception, token is valid
			return true;
		} catch (ExpiredJwtException e) {
			log.error("JWT token has expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Malformed JWT token: {}", e.getMessage());
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		}
		return false;
	}

	// Parse Claims from Token
	private Jws<Claims> getClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
	}
}
