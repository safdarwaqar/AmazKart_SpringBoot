package com.amazkart.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazkart.entity.User;
import com.amazkart.service.UserService;
import com.amazkart.utility.JwtDetailExtractor;

@RestController
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("get-all-users")
	@PreAuthorize("super")
	public List<User> getUsers() throws IOException {
		return userService.getAllUsers();
	}

	@GetMapping("/logged-in")
	public ResponseEntity<?> getUserById() throws IOException {
		User foundUser = userService.findById(JwtDetailExtractor.getUserDetailsFromSpring().getUserId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		return ResponseEntity.ok(foundUser);
	}

	@PutMapping("profile-image")
	public ResponseEntity<?> updateUserProfileImage(@RequestParam("file") MultipartFile file) {
		try {
			userService.updateUserProfileImage(JwtDetailExtractor.getUserDetailsFromSpring().getUserId(), file);
			return ResponseEntity.ok("Profile image updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body("An error occurred while updating the profile image: " + e.getMessage());
		}
	}

}
