package com.shop.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.OrderDTO;
import com.shop.dto.ProductDTO;
import com.shop.entities.Order;
import com.shop.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("all")
	public ResponseEntity<List<OrderDTO>> getAll(){
		List<Order> listOrder = orderService.getAllOrder();
		
		List<OrderDTO> listOrderDTO = new ArrayList<>();
		if (listOrder.size() > 0) {
			for (Order order : listOrder) {
				int id_product = order.getProduct().getId();
				String name_product =  order.getProduct().getName();
				String category =  order.getProduct().getCategory().getName();
				String description = order.getProduct().getDescription();
				String detail = order.getProduct().getDetail();
				
				List<String> image = new ArrayList<>();
				String[] arrImage = order.getProduct().getPicture().split(",");
				for(int i = 0; i < arrImage.length; i++) {
					image.add(arrImage[i]);
				}
				int quantity = order.getProduct().getQuantity();
				float price = order.getProduct().getPrice();
				int count_buy = order.getProduct().getCount_buy();
				String created_by = order.getProduct().getAccount().getUsername();
				String created_at = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss").format(order.getProduct().getCreated_at());
				
				ProductDTO productDTO = new ProductDTO("success",id_product ,name_product,category, description, detail, image, quantity, price, count_buy, created_by, created_at);
				
				OrderDTO orderDTO = new OrderDTO(order.getId(), productDTO, quantity, order.getAmount(), order.getStatus());
				
				listOrderDTO.add(orderDTO);
			}
		
			return new ResponseEntity<List<OrderDTO>>(listOrderDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<OrderDTO>>(HttpStatus.NOT_FOUND);
		
		
	}
	
	@GetMapping("{id_shop}")
	public ResponseEntity<List<OrderDTO>> getOrderOfShop(@PathVariable("id_shop") int id_shop){
		List<Order> listOrder = orderService.getOrderOfShop(id_shop);
		
		List<OrderDTO> listOrderDTO = new ArrayList<>();
		if (listOrder.size() > 0) {
			for (Order order : listOrder) {
				int id_product = order.getProduct().getId();
				String name_product =  order.getProduct().getName();
				String category =  order.getProduct().getCategory().getName();
				String description = order.getProduct().getDescription();
				String detail = order.getProduct().getDetail();
				
				List<String> image = new ArrayList<>();
				String[] arrImage = order.getProduct().getPicture().split(",");
				for(int i = 0; i < arrImage.length; i++) {
					image.add(arrImage[i]);
				}
				int quantity = order.getProduct().getQuantity();
				float price = order.getProduct().getPrice();
				int count_buy = order.getProduct().getCount_buy();
				String created_by = order.getProduct().getAccount().getUsername();
				String created_at = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss").format(order.getProduct().getCreated_at());
				
				ProductDTO productDTO = new ProductDTO("success",id_product ,name_product,category, description, detail, image, quantity, price, count_buy, created_by, created_at);
				
				OrderDTO orderDTO = new OrderDTO(order.getId(), productDTO, quantity, order.getAmount(), order.getStatus());
				
				listOrderDTO.add(orderDTO);
			}
		
			return new ResponseEntity<List<OrderDTO>>(listOrderDTO, HttpStatus.OK);
		}
		
		return new ResponseEntity<List<OrderDTO>>(HttpStatus.NOT_FOUND);
		
	}
}
