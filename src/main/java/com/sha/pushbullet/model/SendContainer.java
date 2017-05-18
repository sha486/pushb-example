package com.sha.pushbullet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sha.pushbullet.endpoint.Note;

public class SendContainer {

	@JsonProperty
	Note note;
	@JsonProperty
	User user;

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
