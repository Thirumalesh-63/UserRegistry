package com.zapcom.userRegistry.globalexceptionhandler;

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
	
	Logger log=LoggerFactory.getLogger(UserController.class);
	
	@ExceptionHandler(usernotfoundexception.class)
	public ResponseEntity<String> usernotfound2(usernotfoundexception exception) {
		return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
	}

}
