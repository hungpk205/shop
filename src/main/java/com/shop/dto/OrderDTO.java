package com.shop.dto;

public class OrderDTO {
	private int id;
	private ProductDTO product;
	private int quantity;
	private float amount;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProductDTO getProduct() {
		return product;
	}
	public void setProduct(ProductDTO product) {
		this.product = product;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public OrderDTO(int id, ProductDTO product, int quantity,
			float amount, int status) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	public OrderDTO() {
		super();
	}
	
}
