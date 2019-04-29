package com.shop.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

}
