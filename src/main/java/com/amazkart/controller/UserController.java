package com.amazkart.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazkart.entity.User;
import com.amazkart.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/get")
	public List<User> getUsers() throws IOException {
		return userService.getAllUsers();
	}

	@PutMapping("/{userId}/profile-image")
	public ResponseEntity<?> updateUserProfileImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
		try {
			userService.updateUserProfileImage(userId, file);
			return ResponseEntity.ok("Profile image updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body("An error occurred while updating the profile image: " + e.getMessage());
		}
	}

}
