package com.shop.service;

import java.util.List;

import com.shop.entities.Cart;

public interface CartService {
	//Get cart of account
	List<Cart> getCartOfAccount(int id_account);
	
	//Get cart product account
	Cart getCartByProduct(int id_account, int id_product);
	
	//Add a cart of acount
	Cart addCartOfAccount(Cart cart);
	
	//Update product in cart
	Cart updateCart(Cart cart);
	
	//Delete Cart of account
	void DeleteCartOfAccount(int id_account);
	
	
}
