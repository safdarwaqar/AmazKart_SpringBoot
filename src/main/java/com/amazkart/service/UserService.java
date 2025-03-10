package com.amazkart.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazkart.entity.Address;
import com.amazkart.entity.User;
import com.amazkart.repository.AddressRepository;
import com.amazkart.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ImageService imageService;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.upload-decode}")
	private String outputPath;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public List<User> getAllUsers() throws IOException {
		List<User> records = userRepository.findAll();
		List<User> updatedRecords = new ArrayList<>();

		for (User user : records) {
			if (user.getProfileImage() != null) {
				// Convert relative path to absolute path
		        Path absolutePath = Paths.get(user.getProfileImage()).toAbsolutePath();

		        // Get the full path including the drive (Windows) or root (Linux/macOS)
		        String fullPath = absolutePath.toString();
				user.setProfileImage(fullPath.replace("\\", "/"));
			}
			updatedRecords.add(user);
		}

		return updatedRecords;
	}

	public User saveUser(User user) {
		// Encode the password before saving the user
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save the user entity
		User savedUser = userRepository.save(user);

		// Ensure that address is set properly before saving
		if (!savedUser.getAddresses().isEmpty()) {
			Address address = savedUser.getAddresses().stream().findFirst().get(); // Get the 0th index
			address.setUser(savedUser); // Link the saved user to the address

			// Save the address entity
			addressRepository.save(address);
		}

		return savedUser; // Return the saved user
	}

	public void updateUserProfileImage(Long userId, MultipartFile file) throws IOException {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File is empty or null!");
		}

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

		// Generate the file path based on the specified upload directory
		String fileName = UUID.randomUUID().toString() + "_"
				+ Optional.ofNullable(file.getOriginalFilename()).orElse("");
		String extention = file.getContentType().split("/")[1];
		Path filePath = Paths.get(uploadDir).resolve(fileName);

		// Save the uploaded file to the specified file path
		logger.debug("Saving file to: " + filePath);
		Files.write(filePath, file.getBytes());

		// Encode the file to Base64 if you need to store it in the database
		String base64Image = imageService.encodeImageToBase64(filePath.toString());
		user.setProfileImage(filePath.toString());
		userRepository.save(user);

		// Optionally, you can keep or delete the file after encoding it
		logger.debug("File saved successfully and encoded to Base64.");
	}

	public Boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}

	public Optional<User> getUserIfExists(String username) {
		return userRepository.findByUsername(username);
	}

}
