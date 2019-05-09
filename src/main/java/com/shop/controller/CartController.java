package com.shop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.CartDTO;
import com.shop.entities.Account;
import com.shop.entities.Cart;
import com.shop.entities.Product;
import com.shop.entities.Role;
import com.shop.request.CartRequest;
import com.shop.response.MessageResponse;
import com.shop.service.AccountService;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ProductService productService;
	
	//Get cart of account
	@GetMapping("customer")
	public ResponseEntity<?> getCartOfAccount(Principal user){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isCustomer = false;
		for (Role role : roles) {
			if (role.getName().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}
		if (!isCustomer) {
			MessageResponse response = new MessageResponse("fail, not customer");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Cart> listCart = cartService.getCartOfAccount(accountLogin.getId());
		if (listCart.isEmpty()) {
			MessageResponse response = new MessageResponse("No have cart");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.NOT_FOUND);
		}
		List<CartDTO> listCartDTO = new ArrayList<>();
		for (Cart cart : listCart) {
			//Get first image of product
			String image = "";
			String listPicture = cart.getProduct().getPicture();
			String[] arrImage = listPicture.split(",");
			if (arrImage.length != 0) {
				image = arrImage[0];
			} 
			CartDTO cartDTO = new CartDTO(cart.getProduct().getId(), cart.getProduct().getName(), image, cart.getQuantity(), cart.getAmount());
			listCartDTO.add(cartDTO);
		}
		
		
		return new ResponseEntity<List<CartDTO>>(listCartDTO, HttpStatus.OK);
	}
	
	//Add cart of account
	@PostMapping("add")
	public ResponseEntity<MessageResponse> addCartOfAccount(Principal user, @RequestBody CartRequest cartRequest){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isCustomer = false;
		for (Role role : roles) {
			if (role.getName().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}
		if (!isCustomer) {
			MessageResponse response = new MessageResponse("fail, not customer");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		//Check exist product
		if (!productService.CheckExistProduct(cartRequest.getId_product())) {
			
			MessageResponse response = new MessageResponse("fail, not found product");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Product product = productService.getOneProduct(cartRequest.getId_product());
		
		if (product.getActive() == 0) {
			MessageResponse response = new MessageResponse("fail, not found product");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		//Check exist product in cart
		if (cartService.getCartByProduct(accountLogin.getId(), cartRequest.getId_product()) != null) {
			MessageResponse response = new MessageResponse("fail, existed product in cart");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		int quantity = 0;
		float amount = 0;
		if (cartRequest.getQuantity() > 0) {
			quantity = cartRequest.getQuantity();
			amount = quantity * product.getPrice();
		}
		
		Cart objCart = new Cart(null, accountLogin, product , quantity, amount);
		Cart cartSave = cartService.addCartOfAccount(objCart);
		
		if (cartSave != null) {
			MessageResponse response = new MessageResponse("success");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
		} else {
			MessageResponse response = new MessageResponse("fail");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	//Edit cart
	@PutMapping("update")
	public ResponseEntity<MessageResponse> updateCart(Principal user, @RequestBody CartRequest cartRequest){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isCustomer = false;
		for (Role role : roles) {
			if (role.getName().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}
		if (!isCustomer) {
			MessageResponse response = new MessageResponse("fail, not customer");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		
		Product product = productService.getOneProduct(cartRequest.getId_product());
		Cart cart = cartService.getCartByProduct(accountLogin.getId(), cartRequest.getId_product());
		
		cart.setQuantity(cartRequest.getQuantity());
		cart.setAmount(cartRequest.getQuantity() * product.getPrice());
		
		//Save cart
		cartService.updateCart(cart);
		MessageResponse response = new MessageResponse("success");
		return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
		
	}
	
	//Delete a product in cart
	@DeleteMapping("delete")
	public ResponseEntity<MessengerUtils> deleteItemCart(Principal user, @RequestBody CartRequest cartRequest){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		cartService.DeleteProductInCart(accountLogin.getId(), cartRequest.getId_product());
		MessengerUtils response = new MessengerUtils(true, "Deleted product id " + cartRequest.getId_product() + "in cart");
		return new ResponseEntity<MessengerUtils>(response, HttpStatus.OK);
	}
	
	//Delete cart of account
	@DeleteMapping("clear")
	public ResponseEntity<?> deleteCartOfAccount(Principal user){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isCustomer = false;
		for (Role role : roles) {
			if (role.getName().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}
		if (!isCustomer) {
			MessageResponse response = new MessageResponse("fail, not customer");
			return new ResponseEntity<MessageResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		cartService.DeleteCartOfAccount(accountLogin.getId());
		MessengerUtils msg = new MessengerUtils(true, "Deleted cart of account");
		return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
		
	}
	
	
}
