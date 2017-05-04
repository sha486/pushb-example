package com.sha.pushbullet;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sha.pushbullet.controller.UserController;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.RegistrationServiceImpl;

public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	RegistrationServiceImpl registrationService;

	@InjectMocks
	private UserController userController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void shouldReturnStatusNotFoundWhenGetAllUsersIsCalledWhenRegisteredUsersIsEmpty() throws Exception {

		// when + then
		mockMvc.perform(get("/users")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnUsersWhenGetAllUsersIsCalledWhenUsersExist() throws Exception {

		// given
		List<User> users = Arrays.asList(
				new User("user001", "accessToken1"),
				new User("user002", "accessToken2"));

		// when
		when(registrationService.getAllUsers()).thenReturn(users);

		// then
		mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)));

	}

	@Test
	public void shouldRetrieveUserByNameIfUserIsRegistered() throws Exception {

		// given
		User user = new User("user001", "accessToken10");

		// when
		when(registrationService.getUserByName(user.getUserName())).thenReturn(user);

		// then
		mockMvc.perform(get("/users/user001")).andExpect(status().isOk())
				.andExpect(jsonPath("$.userName", is("user001")))
				.andExpect(jsonPath("$.accessToken", is("accessToken10")));
	}

	@Test
	public void shouldNotRetrieveUserByNameIfUserNotRegistered() throws Exception {
	
		// when + then
		mockMvc.perform(
				get("/users/user002NotRegisteredYet").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound());
	
	}

	@Test
	public void shouldRegisterNewUserWhenPostRequestIsInvoked() throws Exception {

		// given
		User user = new User("user001", "accessToken1");

		// when + then
		mockMvc.perform(
				post("/users/").content(TestUtils.asJson(user)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated());

	}
	
	@Test
	public void shouldReturnNotAcceptableCodeWhenPostRequestIsInvokedWithInvalidParameter() throws Exception {

		// given
		User user = new User("", "");

		// when + then
		mockMvc.perform(
				post("/users/").content(TestUtils.asJson(user)).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotAcceptable());

	}

}
