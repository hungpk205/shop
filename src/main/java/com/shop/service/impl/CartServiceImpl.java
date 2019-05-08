package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Cart;
import com.shop.repository.CartRepository;
import com.shop.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public List<Cart> getCartOfAccount(int id_account) {
		return cartRepo.getCartOfAccount(id_account);
	}

	@Override
	public void DeleteCartOfAccount(int id_account) {
		 cartRepo.DeleteCartOfAccount(id_account);
	}

	@Override
	public Cart addCartOfAccount(Cart cart) {
		return cartRepo.save(cart);
	}

	@Override
	public Cart updateCart(Cart cart) {
		return cartRepo.save(cart);
	}

	@Override
	public Cart getCartByProduct(int id_account, int id_product) {
		return cartRepo.getCartProductAccount(id_account, id_product);
	}

}
