package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entities.Product;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public int getCountProducts() {
		return 0;
	}

	@Override
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@Override
	public void deleteProductById(int id) {
		productRepo.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteProductByIdCategory(int idCat) {
		productRepo.deleteProductByIdCategory(idCat);
	}

	@Override
	public List<Product> getListProductByIdCategory(int idCat) {
		return productRepo.getListProductByIdCategory(idCat);
	}

	@Override
	public Product addOneProduct(Product objProduct) {
		return productRepo.save(objProduct);
	}

	@Override
	public Product getOneProduct(int id) {
		return productRepo.getOne(id);
	}

	@Override
	public Product changeActive(Product objProduct) {
		return productRepo.save(objProduct);
	}

	@Override
	public List<Product> getTop10Product() {
		return productRepo.getTop10Product();
	}

	@Override
	public List<Product> getProductByIdAccount(int id) {
		return productRepo.getProductByIdAccount(id);
	}

	@Override
	public List<Product> getProductOfCategory(int id) {
		return productRepo.getProductByIdCategory(id);
	}

	@Override
	public boolean CheckExistProduct(int id) {
		return productRepo.existsById(id);
	}

	@Override
	public Product editProduct(Product objProduct) {
		return productRepo.save(objProduct);
	}

	@Override
	public List<Product> getProductOfCategoryShop(int idCat, int idShop) {
		return productRepo.getProductInCategoryOfShop(idCat, idShop);
	}

}
