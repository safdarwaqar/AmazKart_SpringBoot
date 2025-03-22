package com.amazkart.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
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

	public List<User> getAllUsers() {
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
		// Encode the password before saving the user and set userId to null as its auto
		// generated in the database
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setId(null);

		// Save the user entity
		User savedUser = userRepository.save(user);

		// Ensure that address is set properly before saving
		if (!savedUser.getAddresses().isEmpty()) {
			Set<Address> address = savedUser.getAddresses();
			address.stream().forEach(a -> a.setUser(savedUser));
			addressRepository.saveAll(address);
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
		String originalFilename = file.getOriginalFilename();
		if (originalFilename != null && originalFilename.contains(".")) {
		    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		    String fileType = file.getContentType().split("/")[1];
		    log.info("File extension: {}", extension);
		    log.info("File type: {}", fileType);
		} else {
		    throw new IllegalArgumentException("Invalid file or missing extension");
		}

		
		Path filePath = Paths.get(uploadDir).resolve(fileName);

		// Ensure the directory exists, and create it if it doesn't
		Path uploadDirectory = Paths.get(uploadDir);
		if (!Files.exists(uploadDirectory)) {
			Files.createDirectories(uploadDirectory);
			log.error("Upload directory created: {}" , uploadDir);
		}

		// Save the uploaded file to the specified file path
		log.debug("Saving file to: {}" , filePath);
		Files.write(filePath, file.getBytes());

		// Encode the file to Base64 if you need to store it in the database
		String base64Image = imageService.encodeImageToBase64(filePath.toString());
		user.setProfileImage(filePath.toString());
		userRepository.save(user);

		// Optionally, you can keep or delete the file after encoding it
		log.debug("File saved successfully and encoded to Base64.");
	}

	public Boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}

	public Optional<User> getUserIfExists(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}

}
