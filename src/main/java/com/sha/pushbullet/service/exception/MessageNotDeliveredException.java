package com.sha.pushbullet.service.exception;

public class MessageNotDeliveredException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageNotDeliveredException() {

	}

	public MessageNotDeliveredException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageNotDeliveredException(String message) {
		super(message);
	}

	public MessageNotDeliveredException(Throwable cause) {
		super(cause);
	}
}
