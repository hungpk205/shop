package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Order;
import com.shop.repository.OrderRepository;
import com.shop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Override
	public List<Order> getAllOrder() {
		return orderRepo.findAll();
	}

	@Override
	public List<Order> getOrderOfShop(int id_shop) {
		return orderRepo.getOrderOfShop(id_shop);
	}

	@Override
	public Order AddOrder(Order objOrder) {
		return orderRepo.save(objOrder);
	}

}
