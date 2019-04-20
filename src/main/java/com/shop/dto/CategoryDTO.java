package com.shop.dto;

public class CategoryDTO {
	private String success;
	private String name;
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CategoryDTO(String success, String name) {
		super();
		this.success = success;
		this.name = name;
	}
	public CategoryDTO() {
		super();
	}
	
	
}
