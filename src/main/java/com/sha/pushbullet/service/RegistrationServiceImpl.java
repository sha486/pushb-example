package com.sha.pushbullet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.exception.UserAlreadyExistsException;
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

	@Override
	public void registerUser(User user) {

		if (!checkUserExists(user)) {

			users.add(user);
		} else {
			throw new UserAlreadyExistsException();
		}

	}

	public boolean checkUserExists(User user) {

		return users.stream().anyMatch(u -> u.getUserName().equals(user.getUserName()));
	}

	/*
	 * Populate list of users manually for now ...
	 */

	private static List<User> init() {

		List<User> users = Collections.synchronizedList(new ArrayList<User>());

		return users;

	}

	public void updateUser(User user) {

		User updUser = getUserByName(user.getUserName());

		users.remove(updUser);
		users.add(user);

	}

}
