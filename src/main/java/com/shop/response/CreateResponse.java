package com.shop.response;

public class CreateResponse {
	private String message;
	private int id;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CreateResponse(String message, int id) {
		super();
		this.message = message;
		this.id = id;
	}
	public CreateResponse(String message) {
		super();
		this.message = message;
	}
	
}
