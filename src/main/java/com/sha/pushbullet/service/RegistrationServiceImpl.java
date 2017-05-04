package com.sha.pushbullet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.exception.UserNotFoundException;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static List<User> users;

	static {

		users = init();
	}

	@Override
	public User getUserByName(String name) {

		return users.stream().filter(u -> u.getUserName().equals(name)).findFirst()
				.orElseThrow(UserNotFoundException::new);

	}

	@Override
	public List<User> getAllUsers() {
		return users;
	}

	// TODO validate user to prevent duplicate names?
	@Override
	public void registerUser(User user) {
		
		users.add(user);

	}

	/*
	 * Populate list of users manually for now ... 
	 */

	private static List<User> init() {

		List<User> users = new ArrayList<User>(
				Arrays.asList(new User("user001", "accessToken1"), new User("user002", "accessToken2")));

		return users;

	}

}
