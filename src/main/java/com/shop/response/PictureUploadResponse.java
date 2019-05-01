package com.shop.response;

public class PictureUploadResponse {
	private String message;
	private String picture;
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PictureUploadResponse(String message, String picture) {
		super();
		this.message = message;
		this.picture = picture;
	}
	
}	
