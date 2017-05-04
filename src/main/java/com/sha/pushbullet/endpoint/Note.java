package com.sha.pushbullet.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sha.pushbullet.constants.PushType;

public class Note implements PushMessage {
	@JsonProperty
	private String title;
	@JsonProperty
	private String body;
	@JsonProperty
	private final PushType type = PushType.NOTE;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
