package com.shop.service;

import java.util.List;

import com.shop.entities.Product;

public interface ProductService {
	//Get top 10 product
	List<Product> getTop10Product();
	
	//Get count all products
	int getCountProducts();
	
	//Get product by id
	Product getOneProduct(int id);
	
	List<Product> getAll();
	
	//Delete product by id
	void deleteProductById(int id);
	
	//Delete products by id category
	void deleteProductByIdCategory(int idCat);
	
	//Get list product in category by id category
	List<Product> getListProductByIdCategory(int idCat);
	
	Product addOneProduct(Product objProduct);
	
	//Change active product
	Product changeActive(Product objProduct);
	
	//Get list product created by id account
	List<Product> getProductByIdAccount(int id);
	
	//Get list product of category
	List<Product> getProductOfCategory(int id);
}
