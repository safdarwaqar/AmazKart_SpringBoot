package com.amazkart.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.amazkart.jwt.CustomUserDetails;

@Component
public class JwtDetailExtractor {

	public static CustomUserDetails getUserDetailsFromSpring() {
		return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}