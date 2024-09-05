package com.zapcom.userRegistry;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zapcom.common.model.User;
import com.zapcom.userRegistry.controller.UserController;
import com.zapcom.userRegistry.service.Userservice;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistryApplicationTests {

	@Mock
	private Userservice userservice;

	@InjectMocks
	private UserController userController;

	private User user;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setId(1);
		user.setName("Test User");
		// Initialize other fields if necessary
	}

	@Test
	public void testCreateUser() {
		when(userservice.createUser(user)).thenReturn(user);

		ResponseEntity<User> response = userController.CreateUser(user);

		verify(userservice, times(1)).createUser(any(User.class));
		assert (response.getStatusCode()).equals(HttpStatus.CREATED);
		assert (response.getBody()).equals(user);
	}

	@Test
	public void testGetUser() {
		when(userservice.getUser(1)).thenReturn(Optional.of(user));

		ResponseEntity<User> response = userController.getUser(1);

		verify(userservice, times(1)).getUser(1);
		assert (response.getStatusCode()).equals(HttpStatus.OK);
		assert (response.getBody()).equals(user);
	}

	@Test
	public void testGetAllUsers() {
		Page<User> page = new PageImpl<>(Arrays.asList(user));
		when(userservice.getAllUsers(0, 10)).thenReturn(page);

		ResponseEntity<List<User>> response = userController.getAllUsers(0, 10);

		verify(userservice, times(1)).getAllUsers(0, 10);
		assert (response.getStatusCode()).equals(HttpStatus.OK);
		assert (response.getBody()).equals(page.getContent());
	}

	@Test
	public void testUpdateUser() {
		when(userservice.updateUser(1, user)).thenReturn(user);

		ResponseEntity<User> response = userController.updateUser(1, user);

		verify(userservice, times(1)).updateUser(1, user);
		assert (response.getStatusCode()).equals(HttpStatus.OK);
		assert (response.getBody()).equals(user);
	}

	@Test
	public void testDeleteUser() {
		when(userservice.deleteUser(1)).thenReturn(true);

		ResponseEntity<String> response = userController.deleteUser(1);

		verify(userservice, times(1)).deleteUser(1);
		assert (response.getStatusCode()).equals(HttpStatus.OK);
		assert (response.getBody()).equals("user deleted succesfully");
	}

}
