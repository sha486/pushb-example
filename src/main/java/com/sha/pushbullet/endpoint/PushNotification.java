package com.sha.pushbullet.endpoint;

public interface PushNotification {

	long send(String username, PushMessage note);

}
