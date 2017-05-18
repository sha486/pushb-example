package com.sha.pushbullet.service;

import java.util.List;
import com.sha.pushbullet.model.User;

public interface RegistrationService {

	public User getUserByName(String name);

	public List<User> getAllUsers();

	public void registerUser(User user);

}
