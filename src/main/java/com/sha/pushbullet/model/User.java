package com.sha.pushbullet.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sha.pushbullet.util.DateUtils;

public class User {

	@JsonProperty
	private String userName;
	@JsonProperty
	private String accessToken;
	private AtomicLong counter;
	@JsonProperty
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime creationTime;

	public User() {
		this.creationTime = DateUtils.getCurrentDateTime();
		this.counter = new AtomicLong(0);

	}

	public User(String userName, String accessToken) {
		this();
		this.userName = userName;
		this.accessToken = accessToken;
	
	}

	public String getUserName() {
		return userName;
	}

	public String getAccessToken() {
		return accessToken;
	}

	@JsonProperty("numberOfPushedNotifications")
	public long getNumberOfPushedNotifications() {
		return counter.get();
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "RegisteredUser [userName=" + userName + ", accessToken=" + accessToken + "]";
	}

	public long incrementNotificationCount() {
		return counter.incrementAndGet();
	}

}
