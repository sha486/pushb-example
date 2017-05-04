package com.sha.pushbullet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.RegistrationServiceImpl;
import com.sha.pushbullet.service.exception.UserNotFoundException;

@RestController
public class UserController {

	@Autowired
	RegistrationServiceImpl registrationService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = registrationService.getAllUsers();
		if (null == users || users.size() == 0) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

	@RequestMapping(value = "/users/{userName}", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsersByName(@PathVariable String userName) {

		User user = registrationService.getUserByName(userName);

		if (null == user) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(user, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user) {

		if (null == user || user.getUserName().isEmpty()) {
			throw new IllegalArgumentException();

		}
		registrationService.registerUser(user);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public void handleUserNotFoundException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
	}

}
