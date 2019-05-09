package com.shop.response;

import java.util.List;

import com.shop.dto.CartDTO;

public class CartResponse {
	private List<CartDTO> carts;
	private float total_price;
	public List<CartDTO> getCarts() {
		return carts;
	}
	public void setCarts(List<CartDTO> carts) {
		this.carts = carts;
	}
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartResponse(List<CartDTO> carts, float total_price) {
		super();
		this.carts = carts;
		this.total_price = total_price;
	}
	
	
}
