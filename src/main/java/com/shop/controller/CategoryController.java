package com.shop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shop.dto.CategoryDTO;
import com.shop.entities.Category;
import com.shop.entities.Product;
import com.shop.service.CategoryService;
import com.shop.service.FileStorageService;
import com.shop.service.ProductService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService prodcutService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
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
	public ResponseEntity<CategoryDTO> add(@RequestParam("name") String name,
											@RequestParam("image") MultipartFile image){
		//Check name
		if ("".equals(name) || (categoryService.getCategoryByName(name) != null )) {
			CategoryDTO catDTO = new CategoryDTO();
			catDTO.setSuccess("false");
			return new ResponseEntity<CategoryDTO>(catDTO, HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Add this category
			Category objCat = new Category();
			objCat.setName(name);
			
			//Upload image
			String fileName = fileStorageService.storeFile(image);
			String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/category/downloadFile/").path(fileName).toUriString();
			
			objCat.setImage(fileDownloadURI);
			
			categoryService.addNewCategory(objCat);
			CategoryDTO catDTO = new CategoryDTO("true", objCat.getName(), objCat.getImage());
			return new ResponseEntity<CategoryDTO>(catDTO,HttpStatus.OK);
			
		}
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile (@PathVariable String fileName, HttpServletRequest request){
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.info("Could not determine file type.");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		//return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
		return new ResponseEntity<Resource>(resource, HttpStatus.OK);
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
