package com.sha.pushbullet.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.sha.pushbullet.model.User;
import com.sha.pushbullet.service.RegistrationService;
import com.sha.pushbullet.service.exception.MessageNotDeliveredException;

@Component
public class PushNotificationImpl implements PushNotification {

	@Value("${pushbullet.push.url}")
	private String url;

	@Autowired
	RegistrationService registrationService;

	@Override
	public long send(String username, PushMessage note) {
		User user = registrationService.getUserByName(username);
		String accessToken = user.getAccessToken();

		callPushBullet(note, accessToken);

		return user.incrementNotificationCount();
	}

	private void callPushBullet(PushMessage note, String accessToken) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.set("Content-Type", "application/json");
		headers.set("Access-Token", accessToken);

		HttpEntity entity = new HttpEntity<>(note, headers);

		RestTemplate template = new RestTemplate();
		HttpStatus statusCode;
		try {
			statusCode = template.exchange(url, HttpMethod.POST, entity, String.class).getStatusCode();
		} catch (HttpClientErrorException e) {
			throw new MessageNotDeliveredException(e);
		}
	}

}
