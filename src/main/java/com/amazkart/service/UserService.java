package com.amazkart.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.amazkart.entity.User;

public interface UserService {

	List<User> getAllUsers();

	User saveUser(User user);

	void updateUserProfileImage(Long userId, MultipartFile file) throws IOException;

	Boolean userExists(String username);

	Optional<User> getUserIfExists(String username);

}
