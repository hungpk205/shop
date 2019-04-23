package com.shop.dto;

public class CategoryDTO {
	private String success;
	private String name;
	private String image;
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public CategoryDTO(String success, String name, String image) {
		super();
		this.success = success;
		this.name = name;
		this.image = image;
	}
	public CategoryDTO() {
		super();
	}
	
	
}
