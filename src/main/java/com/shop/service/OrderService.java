package com.shop.service;

import java.util.List;

import com.shop.entities.Order;

public interface OrderService {
	//Get all order
	List<Order> getAllOrder();
	
	//Get order of shop
	List<Order> getOrderOfShop(int id_shop);
	
}
