package com.shop.request;

import javax.validation.constraints.NotBlank;

public class ProductRequest {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	@NotBlank
	private String detail;
	@NotBlank
	private String picture;
	private int quantity;
	private float price;
	private int id_category;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getId_category() {
		return id_category;
	}
	public void setId_category(int id_category) {
		this.id_category = id_category;
	}
	public ProductRequest(String name, String description, String detail, String picture, int quantity, float price,
			int id_category) {
		super();
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.picture = picture;
		this.quantity = quantity;
		this.price = price;
		this.id_category = id_category;
	}
	public ProductRequest() {
		super();
	}
	
}
