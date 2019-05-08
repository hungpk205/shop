package com.shop.utils;

public class MessengerUtils {
	private boolean success;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public MessengerUtils(boolean success) {
		super();
		this.success = success;
	}
	

	public MessengerUtils(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	

	public MessengerUtils() {
		super();
	}
	
	
}
