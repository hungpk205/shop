package com.shop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Product;
import com.shop.service.ProductService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("all")
	public ResponseEntity<List<Product>> getAll(){
		List<Product> listProduct = productService.getAll();
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(listProduct, HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value = {"top/{page}", "top"})
	public ResponseEntity<List<Product>> getTop12Pagination(@PathVariable("page") Optional<Integer> page){
		int currentPage = 0;
		if (page.isPresent()) {
			currentPage = page.get();
		}
		
		if (currentPage < 0 || currentPage > (productService.getCountProducts())/2 + 1) {
			currentPage = 0;
		}
		Pageable pageable = PageRequest.of(currentPage, 2);
		List<Product> listProduct = productService.getTop12Pagination(pageable);
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(listProduct,HttpStatus.OK);
		}
		
	}
	

}
