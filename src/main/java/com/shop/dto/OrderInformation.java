package com.shop.dto;

public class OrderInformation {
	private int id;
	private SellerDTO seller;
	private String product;
	private int quantity;
	private float amount;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
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
	
	public SellerDTO getSeller() {
		return seller;
	}
	public void setSeller(SellerDTO seller) {
		this.seller = seller;
	}
	
	public OrderInformation(int id, SellerDTO seller, String product, int quantity, float amount, int status) {
		super();
		this.id = id;
		this.seller = seller;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	public OrderInformation() {
		super();
	}
	
}
