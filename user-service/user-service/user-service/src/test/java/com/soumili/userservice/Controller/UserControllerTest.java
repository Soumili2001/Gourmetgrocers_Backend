package com.soumili.userservice.Controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumili.userservice.controller.UserController;
import com.soumili.userservice.entities.User;
import com.soumili.userservice.payloads.LoginDto;
import com.soumili.userservice.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	UserService userService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testregisterUser() throws Exception {
		User user = new User(11, "Soumili", "12345#ABC", "sarsoubis@gmail.com", "Soumili Biswas", "user", false, null,
				null);
		doNothing().when(userService).registerUser(user);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isOk());

	}

	@Test
	public void testloginUser() throws Exception {
		LoginDto logindto = new LoginDto("Soumili", "12345#ABC");
		User user = new User(11, "Soumili", "12345#ABC", "sarsoubis@gmail.com", "Soumili Biswas", "user", false, null,
				null);
		when(userService.loginUser(logindto)).thenReturn(user);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(logindto))).andExpect(status().isOk());

	}

	@Test
	public void testLogOutUser() throws Exception {

		doNothing().when(userService).logoutUser("Soumili");
		mockMvc.perform(post("/user/logout/{username}", "Soumili").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testViewAllUsers() throws Exception {
		List<User> userList = Arrays.asList(
				new User(11, "Soumili", "12345#ABC", "sarsoubis@gmail.com", "Soumili Biswas", "user", false, null,
						null),
				new User(12, "Sarthak", "123456#ABC", "sarthak@gmail.com", "Sarthak Biswas", "user", false, null, null),
				new User(13, "Sanjiban", "123457#ABC", "sanjibanbis@gmail.com", "Sanjiban Biswas", "user", false, null,
						null));
		when(userService.findAllUsers("admin")).thenReturn(userList);
		mockMvc.perform(get("/user/All/{username}", "admin").contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk()).andExpect(jsonPath("$[0].username").value("Soumili"));

	}

	@Test
	public void testViewUser() throws Exception {
		User user = new User(11, "Soumili", "12345#ABC", "sarsoubis@gmail.com", "Soumili Biswas", "user", false, null,
				null);
		when(userService.findUserByUsername("Soumili")).thenReturn(user);
		mockMvc.perform(get("/user/find/{username}", "Soumili").contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk()).andExpect(jsonPath("$.username").value("Soumili"));

	}

}
