package com.shop.response;

public class RegisterResponse {
	private String message;
	private int id_account;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId_account() {
		return id_account;
	}
	public void setId_account(int id_account) {
		this.id_account = id_account;
	}
	public RegisterResponse(String message, int id_account) {
		super();
		this.message = message;
		this.id_account = id_account;
	}
	public RegisterResponse(String message) {
		super();
		this.message = message;
	}
	public RegisterResponse() {
		super();
	}
}
