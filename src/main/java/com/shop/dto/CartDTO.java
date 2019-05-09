package com.shop.dto;

public class CartDTO {
	private int id_product;
	private String name_product;
	private String image;
	private float price;
	private int quantity;
	private float amount;
	public int getId_product() {
		return id_product;
	}
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}
	public String getName_product() {
		return name_product;
	}
	public void setName_product(String name_product) {
		this.name_product = name_product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	public CartDTO(int id_product, String name_product, float price, String image, int quantity, float amount) {
		super();
		this.id_product = id_product;
		this.name_product = name_product;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
		this.amount = amount;
	}
	public CartDTO() {
		super();
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
}
