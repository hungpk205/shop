package com.shop.request;

public class CartRequest {
	
	private int id_product;
	private int quantity;
	
	public int getId_product() {
		return id_product;
	}
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public CartRequest(int id_product, int quantity) {
		super();
		this.id_product = id_product;
		this.quantity = quantity;
	}
	public CartRequest() {
		super();
	}
	
}
