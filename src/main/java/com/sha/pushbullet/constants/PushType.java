package com.sha.pushbullet.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PushType {
	@JsonProperty("note")
	NOTE, 
	@JsonProperty("link")
	LINK, 
	@JsonProperty("file")
	FILE
}
