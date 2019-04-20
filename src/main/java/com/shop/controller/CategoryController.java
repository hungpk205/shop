package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.CategoryDTO;
import com.shop.entities.Category;
import com.shop.service.CategoryService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//Get all categories
	@GetMapping("all")
	public ResponseEntity<List<Category>> getAll(){
		List<Category> listCat = categoryService.getAllCategory();
		if (listCat.size() > 0) {
			return new ResponseEntity<List<Category>>(listCat, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Add new category
	@PostMapping("add")
	public ResponseEntity<CategoryDTO> add(@RequestBody Category objCat){
		//Check name
		if ("".equals(objCat.getName())) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Add this category
			categoryService.addNewCategory(objCat);
			CategoryDTO catDTO = new CategoryDTO("true", objCat.getName());
			return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			
		}
	}
	
	//Edit category
	@PutMapping("{idCat}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable("idCat") int idCat, @RequestBody Category objCat){
		Category cat = categoryService.getCateogry(idCat);
		if (cat == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		} else {
			if ("".equals(objCat.getName())) {
				return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				cat.setName(objCat.getName());
				Category catEdited =  categoryService.editCategory(cat);
				CategoryDTO catDTO = new CategoryDTO("true", catEdited.getName());
				return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			}
			
			
		}
	}
	
}
