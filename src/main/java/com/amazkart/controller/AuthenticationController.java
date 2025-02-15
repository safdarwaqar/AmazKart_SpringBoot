package com.amazkart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazkart.entity.AuthenticationRequest;
import com.amazkart.entity.User;
import com.amazkart.jwtcfg.CustomUserDetailsService;
import com.amazkart.jwtcfg.JwtUtil;
import com.amazkart.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping("/signup")
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@PostMapping()
	public Map<String, String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			// Authenticate the user
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		// Load user details and generate JWT token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails); // Pass UserDetails to generateToken

		return Map.of("token", jwt);
	}
}