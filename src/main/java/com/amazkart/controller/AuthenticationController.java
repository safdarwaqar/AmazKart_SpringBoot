package com.amazkart.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amazkart.entity.AuthenticationRequest;
import com.amazkart.entity.User;
import com.amazkart.exception.InvalidCredentialsException;
import com.amazkart.exception.UserAlreadyExistsException;
import com.amazkart.jwtcfg.CustomUserDetailsService;
import com.amazkart.jwtcfg.JwtUtil;
import com.amazkart.service.UserServiceImpl;
import com.amazkart.utility.UserAgentLogger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import ua_parser.Client;
import ua_parser.Parser;

@RestController
@RequestMapping("auth")
@Transactional
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserServiceImpl userService;
	private final CustomUserDetailsService userDetailsService;
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UserServiceImpl userService, CustomUserDetailsService userDetailsService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.CREATED)
	public Map<String, String> createUser(@RequestBody User user, HttpServletRequest request) {

		userService.getUserIfExists(user.getUsername()).ifPresent(u -> {
			throw new UserAlreadyExistsException("User already exists");
		});

		User savedUser = userService.saveUser(user);
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		String jwt = jwtUtil.generateToken(userDetails);
		return Map.of("token", jwt, "username", savedUser.getUsername(), "firstName", savedUser.getFirstName(),
				"lastName", savedUser.getLastName());
	}

	@PostMapping()
	public Map<String, String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException(e.getLocalizedMessage().toString());
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return Map.of("token", jwt);
	}
}
