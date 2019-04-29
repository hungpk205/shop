package com.shop.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.shop.entities.Product;

public interface ProductService {
	//Get top 12 product pagination
	List<Product> getTop12Pagination(Pageable pageable);
	
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
}
