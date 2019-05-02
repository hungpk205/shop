package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Order;
import com.shop.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("all")
	public ResponseEntity<List<Order>> getAll(){
		List<Order> listOrder = orderService.getAllOrder();
		return new ResponseEntity<List<Order>>(listOrder, HttpStatus.OK);
	}
	
	@GetMapping("{id_shop}")
	public ResponseEntity<List<Order>> getOrderOfShop(@PathVariable("id_shop") int id_shop){
		List<Order> listOrder = orderService.getOrderOfShop(id_shop);
		return new ResponseEntity<List<Order>>(listOrder, HttpStatus.OK);
	}
}
