package com.amazkart.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		logger.error("UserAlreadyExistsException: {}", ex.getMessage());
		return createErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UserNotFoundException ex) {
		logger.error("UserNotFoundException: {}", ex.getMessage());
		return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Helper method to create error response
	private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
		Map<String, Object> errorDetails = Map.of("timestamp", LocalDateTime.now(), "message", message, "status",
				status.value());
		return new ResponseEntity<>(errorDetails, status);
	}
}
