package com.shop.service;

import java.util.List;

import com.shop.entities.Category;

public interface CategoryService {
	//Get all list
	List<Category> getAllCategory();
	
	//Add a category
	Category addNewCategory(Category objCat);
	
	//Get category by ID
	Category getCateogry(int id);
	
	//Edit category
	Category editCategory(Category objCat);
}
