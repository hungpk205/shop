package com.shop.service;

import java.util.List;

import com.shop.entities.Product;

public interface ProductService {
	//Get top 12 product
	List<Product> getTop12Product();
	
	//Get count all products
	int getCountProducts();
	
	//Get product by id
	Product getOneProduct(int id);
	
	//Get all product
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
	
	//Get list product in category of shop
	List<Product> getProductOfCategoryShop(int idCat, int idShop);
	
	//Check exist product
	boolean CheckExistProduct(int id);
	
	//Edit product
	Product editProduct(Product objProduct);
	
	//Top 12 product buy
	List<Product> getTop12ProductBuy();
	
	//Increase count buy product id
	void IncreaseCountBuyProductById(int idProduct);
	
	//Reduce quantity
	void ReduceQuantityProduct(int id_product, int number);
}
