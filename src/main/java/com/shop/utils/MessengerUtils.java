package com.shop.utils;

public class MessengerUtils {
	private String success;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public MessengerUtils(String success) {
		super();
		this.success = success;
	}
	

	public MessengerUtils(String success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public MessengerUtils() {
		super();
	}
	
	
}
