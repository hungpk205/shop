package com.shop.service;

import java.util.List;

import com.shop.entities.Category;

public interface CategoryService {
	//Get all list
	List<Category> getAllCategory();
	
	//Add a category
	Category addNewCategory(Category objCat);
	
	//Get category by ID
	Category getCateogryById(int id);
	
	//Get category by Name
	Category getCategoryByName(String name);
	
	
	//Edit category
	Category editCategory(Category objCat);
	
	//Delete category by Id
	void deleteCategory(int id);
	
	//Exist category by id
	boolean CheckExitCategoryById(int id);
}
