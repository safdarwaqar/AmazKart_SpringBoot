package com.amazkart.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	// Directory where images will be saved
	@Value("${file.upload-dir}")
	private static String IMAGE_DIRECTORY;

	// Encode image to Base64 string
	public String encodeImageToBase64(String imagePath) throws IOException {
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		return Base64.getEncoder().encodeToString(imageBytes);
	}

	// Decode Base64 string to image file
	public void decodeBase64ToImage(String base64Image, String outputPath) throws IOException {
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);
		Files.write(Paths.get(outputPath), imageBytes);
	}

	// Save file to the directory and return the file name
	public String saveFile(byte[] fileBytes, String fileExtension) throws IOException {
		// Ensure the directory exists
		Path directoryPath = Paths.get(IMAGE_DIRECTORY);
		if (!Files.exists(directoryPath)) {
			Files.createDirectories(directoryPath);
		}

		// Generate a unique file name
		String fileName = UUID.randomUUID().toString() + "." + fileExtension;
		Path filePath = Paths.get(IMAGE_DIRECTORY + fileName);

		// Save the file
		Files.write(filePath, fileBytes);

		return fileName;
	}

	// Retrieve file by file name
	public byte[] getFile(String fileName) throws IOException {
		Path filePath = Paths.get(IMAGE_DIRECTORY + fileName);
		if (Files.exists(filePath)) {
			return Files.readAllBytes(filePath);
		} else {
			throw new IOException("File not found: " + fileName);
		}
	}

	// Delete file by file name
	public void deleteFile(String fileName) throws IOException {
		Path filePath = Paths.get(IMAGE_DIRECTORY + fileName);
		if (Files.exists(filePath)) {
			Files.delete(filePath);
		} else {
			throw new IOException("File not found: " + fileName);
		}
	}
}