package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.CategoryDTO;
import com.shop.entities.Category;
import com.shop.entities.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService prodcutService;
	
	
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
		if ("".equals(objCat.getName()) || (categoryService.getCategoryByName(objCat.getName()) != null )) {
			CategoryDTO catDTO = new CategoryDTO();
			catDTO.setSuccess("false");
			return new ResponseEntity<CategoryDTO>(catDTO, HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Add this category
			
			categoryService.addNewCategory(objCat);
			CategoryDTO catDTO = new CategoryDTO("true", objCat.getName(), objCat.getImage());
			return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			
		}
	}
	
	//Edit category
	@PutMapping("{idCat}")
	public ResponseEntity<CategoryDTO> edit(@PathVariable("idCat") int idCat, @RequestBody Category objCat){
		Category cat = categoryService.getCateogryById(idCat);
		if (cat == null) {
			return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_FOUND);
		} else {
			if ("".equals(objCat.getName())) {
				return new ResponseEntity<CategoryDTO>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				cat.setName(objCat.getName());
				Category catEdited =  categoryService.editCategory(cat);
				CategoryDTO catDTO = new CategoryDTO("true", catEdited.getName(), objCat.getImage());
				return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			}
		}
	}
	
	//Delete category
	@DeleteMapping("{idCat}")
	public ResponseEntity<MessengerUtils> delete(@PathVariable("idCat") int idCat){
		if (categoryService.getCateogryById(idCat) == null) {
			MessengerUtils msg = new MessengerUtils("false", "Not found category id " + idCat);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_FOUND);
		} else {
			if (prodcutService.getListProductByIdCategory(idCat).isEmpty()) {
				categoryService.deleteCategory(idCat);
				MessengerUtils msg = new MessengerUtils("true", "Deleted category id " + idCat);
				return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
			} else {
				MessengerUtils msg = new MessengerUtils("false", "Can not delete category id " + idCat);
				return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_ACCEPTABLE);
			}
		}
	}
	
	@GetMapping("product/{idCat}")
	public ResponseEntity<List<Product>> getListProductinCategory(@PathVariable("idCat") int idCat){
		List<Product> listProduct = prodcutService.getListProductByIdCategory(idCat);
		if (listProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(listProduct, HttpStatus.OK);
		}
	}
	
}
