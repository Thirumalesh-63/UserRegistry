package com.zapcom.userRegistry.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zapcom.common.model.User;
import com.zapcom.userRegistry.service.Userservice;

@RestController
@RequestMapping("userregistry")

public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public Userservice userservice;

	@PostMapping("/user")
	public ResponseEntity<User> CreateUser(@RequestBody User user) {
		return new ResponseEntity<>(userservice.createUser(user), HttpStatus.CREATED);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {

		return new ResponseEntity<>(userservice.getUser(id).orElse(null), HttpStatus.OK);

	}
	
	@GetMapping("/userbyemail/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {

		return new ResponseEntity<>(userservice.getUserByEmail(email), HttpStatus.OK);

	}

	@GetMapping("/admin/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<User> users = userservice.getAllUsers(page, size);
		return new ResponseEntity<>(users.getContent(), HttpStatus.OK);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
		User updatedUser = userservice.updateUser(id, userDetails);
		if (updatedUser != null) {
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		boolean isDeleted = userservice.deleteUser(id);
		if (isDeleted) {
			return new ResponseEntity<>("user deleted succesfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
