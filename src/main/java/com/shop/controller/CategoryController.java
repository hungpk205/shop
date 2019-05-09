package com.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.CategoryDTO;
import com.shop.entities.Account;
import com.shop.entities.Category;
import com.shop.entities.Role;
import com.shop.service.AccountService;
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
	
	@Autowired
	private AccountService accountSerivce;
	
	
	//Get all categories
	@GetMapping("view/all")
	public ResponseEntity<List<Category>> getAll(){
		List<Category> listCat = categoryService.getAllCategory();
		if (listCat.size() > 0) {
			return new ResponseEntity<List<Category>>(listCat, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	//Get category by id
	@GetMapping("view/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") int id){
		 Category objCat = categoryService.getCateogryById(id);
		 if (objCat == null) {
			 return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
		 } else {
			 return new ResponseEntity<Category>(objCat, HttpStatus.OK);
		 }
		 
	}
	
	//Add new category
	@PostMapping("add")
	public ResponseEntity<CategoryDTO> add(Principal user, HttpServletRequest request ,@Valid @RequestBody Category objCat, BindingResult br){
		//Check empty value
		if (br.hasErrors()) {
			CategoryDTO catDTO = new CategoryDTO("false", null, null);
			return new ResponseEntity<CategoryDTO>(catDTO, HttpStatus.NOT_ACCEPTABLE);
		}
		
		//Check role
		Account accountLogin = accountSerivce.getAccountByUsername(user.getName());
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			CategoryDTO catDTO = new CategoryDTO("false", null, null);
			return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.FORBIDDEN);
		}
		
		
		
		categoryService.addNewCategory(objCat);
		CategoryDTO catDTO = new CategoryDTO("true", objCat.getName(), objCat.getImage());
		return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			
	}
	
	//Edit category
	@PutMapping("{idCat}")
	public ResponseEntity<MessengerUtils> edit(Principal user ,@PathVariable("idCat") int idCat, @RequestBody Category objCat){
		Category cat = categoryService.getCateogryById(idCat);
		
		//Check role
		Account accountLogin = accountSerivce.getAccountByUsername(user.getName());
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessengerUtils msg = new MessengerUtils(false, "Not have role");
			return new ResponseEntity<MessengerUtils>(msg,HttpStatus.FORBIDDEN);
		}
		
		
		if (cat == null) {
			MessengerUtils msg = new MessengerUtils(false, "Not found category id " + idCat);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_FOUND);
		} else {
			if (!objCat.getName().equals("")) {
				cat.setName(objCat.getName());
			} 
			if (!objCat.getImage().equals("")) {
				cat.setImage(objCat.getImage());
			}
			categoryService.editCategory(cat);
			
			MessengerUtils msg = new MessengerUtils(true, "Edited category id " + idCat);
			return new ResponseEntity<MessengerUtils>(msg,HttpStatus.OK);
		}
	}
	
	//Delete category
	@DeleteMapping("{idCat}")
	public ResponseEntity<MessengerUtils> delete(Principal user ,@PathVariable("idCat") int idCat){
		
		//Check role
		Account accountLogin = accountSerivce.getAccountByUsername(user.getName());
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessengerUtils msg = new MessengerUtils(false, "Not have role");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.FORBIDDEN);
		}
		
		//Check exist category
		if (categoryService.getCateogryById(idCat) == null) {
			MessengerUtils msg = new MessengerUtils(false, "Not found category id " + idCat);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_FOUND);
		} else {
			if (prodcutService.getListProductByIdCategory(idCat).isEmpty()) {
				categoryService.deleteCategory(idCat);
				MessengerUtils msg = new MessengerUtils(true, "Deleted category id " + idCat);
				return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
			} else {
				MessengerUtils msg = new MessengerUtils(false, "Can not delete category id " + idCat);
				return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_ACCEPTABLE);
			}
		}
	}
	
	/*@GetMapping("product/{idCat}")
	public ResponseEntity<List<ProductDTO>> getListProductinCategory(@PathVariable("idCat") int idCat){
		List<Product> listProduct = prodcutService.getListProductByIdCategory(idCat);
		if (listProduct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			List<ProductDTO> listProductDTO = new ArrayList<>();
			
			
			return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
		}
	}*/
	
}
