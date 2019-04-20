package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Category;
import com.shop.repository.CategoryRepository;
import com.shop.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<Category> getAllCategory() {
		List<Category> listCat = categoryRepo.findAll();
		return listCat;
	}

	@Override
	public Category addNewCategory(Category objCat) {
		return categoryRepo.save(objCat);
	}

	@Override
	public Category getCateogry(int id) {
		return categoryRepo.getOne(id);
	}

	@Override
	public Category editCategory(Category objCat) {
		return categoryRepo.save(objCat);
	}

}
