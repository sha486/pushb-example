package com.sha.pushbullet;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.RegistrationService;
import com.sha.pushbullet.service.RegistrationServiceImpl;
import com.sha.pushbullet.service.exception.UserAlreadyExistsException;
import com.sha.pushbullet.service.exception.UserNotFoundException;

public class RegistrationServiceImplTest {

	private RegistrationService registrationService;

	@Before
	public void init() {
		registrationService = new RegistrationServiceImpl();
	}

	@Test(expected = UserNotFoundException.class)
	public void shouldThrowUserNotFoundExceptionWhenUserIsNotRegistered() throws Exception {
		registrationService.getUserByName("user911");
	}

	@Test
	public void shouldReturnUserWhenUserIsRegistered() throws Exception {

		// given registered user
		User actualUser = new User("user003", "user003Token");
		registrationService.registerUser(actualUser);

		// when user is retrieved by name
		User expectedUser = registrationService.getUserByName("user003");

		// then

		assertEquals(expectedUser, actualUser);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void shouldThrowUserAlreadyExistsExceptionWhenDuplicateUserIsRegistered() throws Exception {

		// given registered user
		User actualUser = new User("user004", "user004Token");
		registrationService.registerUser(actualUser);

		registrationService.registerUser(actualUser);

	}

}
