package com.shop.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.OrderDTO;
import com.shop.dto.ProductDTO;
import com.shop.entities.Account;
import com.shop.entities.Order;
import com.shop.entities.Role;
import com.shop.response.MessageResponse;
import com.shop.service.AccountService;
import com.shop.service.OrderService;

@RestController
@RequestMapping("api/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private AccountService accountService;
	
	
	@GetMapping("alll")
	public ResponseEntity<List<Order>> getAlll(){
		List<Order> listOrder = orderService.getAllOrder();
		return new ResponseEntity<List<Order>>(listOrder, HttpStatus.OK);
	}
	
	
	@GetMapping("all")
	public ResponseEntity<?> getAll(Principal user){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessageResponse response = new MessageResponse("Not have role");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.FORBIDDEN);
		}
		
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
				String created_at = new SimpleDateFormat("dd/MM/yyyy").format(order.getProduct().getCreated_at());
				
				ProductDTO productDTO = new ProductDTO("success",id_product ,name_product,category, description, detail, image, quantity, price, count_buy, created_by, created_at);
				
				OrderDTO orderDTO = new OrderDTO(order.getId(), productDTO, quantity, order.getAmount(), order.getStatus());
				
				listOrderDTO.add(orderDTO);
			}
		
			return new ResponseEntity<List<OrderDTO>>(listOrderDTO, HttpStatus.OK);
		}
		MessageResponse response = new MessageResponse("Not have order");
		return new ResponseEntity<MessageResponse>(response, HttpStatus.NOT_FOUND);
		
		
	}
	
	@GetMapping("shop")
	public ResponseEntity<?> getOrderOfShop(Principal user){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		boolean isShop = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("SHOP OWNER")) {
				isShop = true;
				break;
			}
		}
		if (!isShop) {
			MessageResponse response = new MessageResponse("Not have role");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.FORBIDDEN);
		}
		
		
		List<Order> listOrder = orderService.getOrderOfShop(accountLogin.getId());
		
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
				String created_at = new SimpleDateFormat("dd/MM/yyyy").format(order.getProduct().getCreated_at());
				
				ProductDTO productDTO = new ProductDTO("success",id_product ,name_product,category, description, detail, image, quantity, price, count_buy, created_by, created_at);
				
				OrderDTO orderDTO = new OrderDTO(order.getId(), productDTO, quantity, order.getAmount(), order.getStatus());
				
				listOrderDTO.add(orderDTO);
			}
		
			return new ResponseEntity<List<OrderDTO>>(listOrderDTO, HttpStatus.OK);
		}
		MessageResponse response = new MessageResponse("Not found order");
		return new ResponseEntity<MessageResponse>(response,HttpStatus.NOT_FOUND);
		
	}
}
