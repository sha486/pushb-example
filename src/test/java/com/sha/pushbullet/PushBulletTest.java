package com.sha.pushbullet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sha.pushbullet.endpoint.Note;
import com.sha.pushbullet.endpoint.PushNotification;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.RegistrationServiceImpl;
import com.sha.pushbullet.service.exception.MessageNotDeliveredException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushBulletTest {

	@MockBean
	RegistrationServiceImpl registrationService;

	@Autowired
	PushNotification pushNotification ;
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void shouldPushNoteMessageGivenAuthorisedUser() {

		// given
		String authorisedUser = "user001";
		User user = new User(authorisedUser, "o.Ja3pMqAgrnezP8fnIjIwc49Kc3hRa8vJ");
		Note note = new Note();
		note.setBody("I am sending a note ... ");
		note.setTitle("My first push notification!");
		// when
		when(registrationService.getUserByName(user.getUserName())).thenReturn(user);

		long expectedNotificationCount = user.getNumberOfPushedNotifications() + 1;
		long actualNotificationCount = pushNotification.send(authorisedUser, note);

		// then
		assertEquals(expectedNotificationCount, actualNotificationCount);

	}

	@Test(expected = MessageNotDeliveredException.class)
	public void shouldThrowExceptionGivenUnauthorisedUser() {

		String unauthorisedUser = "unauthorisedUser";

		// given
		User user = new User(unauthorisedUser, "fakeToken");
		when(registrationService.getUserByName(unauthorisedUser)).thenReturn(user);

		Note note = new Note();
		note.setBody("I am trying to send a note ... ");
		note.setTitle("My first push notification !");

		pushNotification.send(unauthorisedUser, note);

	}
}
