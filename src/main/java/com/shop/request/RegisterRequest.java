package com.shop.request;

import javax.validation.constraints.NotBlank;

public class RegisterRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String fullname;
	@NotBlank
	private String email;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RegisterRequest(@NotBlank String username,
			@NotBlank String password,@NotBlank String fullname, @NotBlank String email) {
		super();
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
	}
	public RegisterRequest() {
		super();
	}
	
	
}
