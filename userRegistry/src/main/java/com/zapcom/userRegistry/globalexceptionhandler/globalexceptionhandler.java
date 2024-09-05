package com.zapcom.userRegistry.globalexceptionhandler;

import java.nio.file.AccessDeniedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zapcom.userRegistry.controller.UserController;
import com.zapcom.userRegistry.exceptions.usernotfoundexception;

@ControllerAdvice
public class globalexceptionhandler {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@ExceptionHandler(usernotfoundexception.class)
	public ResponseEntity<String> usernotfound2(usernotfoundexception exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		String message = "Access Denied: You do not have permission to access this resource.";
		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	// Handle Unauthorized Access Exceptions
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleUnauthorizedAccess(RuntimeException ex) {
		System.err.println("RuntimeException: " + ex.getMessage());

		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

}
