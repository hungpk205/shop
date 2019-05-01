package com.shop.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.ProductDTO;
import com.shop.entities.Account;
import com.shop.entities.Category;
import com.shop.entities.Permission;
import com.shop.entities.Product;
import com.shop.entities.Role;
import com.shop.response.CreateResponse;
import com.shop.response.MessageResponse;
import com.shop.service.AccountService;
import com.shop.service.CategoryService;
import com.shop.service.FileStorageService;
import com.shop.service.PermissionService;
import com.shop.service.ProductService;

@RestController
@RequestMapping("api/product")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	
	@GetMapping("all")
	public ResponseEntity<List<ProductDTO>> getAll(){
		List<Product> listProduct = productService.getAll();
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		
		for (Product product : listProduct) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
			
		}
		
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
		}
	}
	
	//
	@GetMapping("top")
	public ResponseEntity<List<ProductDTO>> getTop10Product(){
		List<Product> listProduct = productService.getTop10Product();
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		
		for (Product product : listProduct) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
			
		}
		
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
		}
	}
	
	//Get product by id
	@GetMapping("{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") int id){
		//Check product
		if (!productService.CheckExistProduct(id)) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setMessage("fail: not found");
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.NOT_FOUND);
			
		} else {
			Product objProduct = productService.getOneProduct(id);
			
			List<String> listImage = new ArrayList<>();
			String dbImage = objProduct.getPicture();
			String[] arrImage = dbImage.split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(objProduct.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",objProduct.getId(), objProduct.getName(), objProduct.getCategory().getName(), objProduct.getDescription(), objProduct.getDetail(), listImage, objProduct.getQuantity(), objProduct.getPrice(),objProduct.getCount_buy(), objProduct.getAccount().getUsername(), time);
			
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
		}
	}
	
	
	
	
	//Add one product
	@PostMapping("add/{id_account}")
	public ResponseEntity<CreateResponse> add(
										@PathVariable("id_account") int id_account,
										@Valid @RequestBody Product objProduct, BindingResult br
			){
		
		if (!categoryService.CheckExitCategoryById(objProduct.getCategory().getId())) {
			CreateResponse response = new CreateResponse("fail: not exist category id " + objProduct.getCategory().getId());
			return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		//Check exit category by id
		Category category = categoryService.getCateogryById(objProduct.getCategory().getId());
				
		//Check valid
		if (br.hasErrors()) {
			CreateResponse response = new CreateResponse("fail: invalid");
			return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		//Check exist account
		if (!accountService.CheckExistById(id_account)) {
			CreateResponse response = new CreateResponse("fail: not exist account id " + id_account);
			return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_FOUND);
		}
		//Check role account
		Account account = accountService.getAccountById(id_account);
		
		Set<Role> role = account.getRole();
		String nameRole = "";
		for (Role item : role) {
			nameRole = item.getName();
		}
		if (!nameRole.equals("SHOP OWNER")) {
			CreateResponse response = new CreateResponse("fail: role is not SHOP OWNER");
			return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Check permission CREATE_PRODUCT
			boolean createProduct = false;
			Set<Permission> permission = account.getPermission();
			Permission permissionCreateProduct = permissionService.getPermissionByName("CREATE_PRODUCT");
			for (Permission item : permission) {
				if (item.equals(permissionCreateProduct)) {
					createProduct = true;
					break;
				}
			}
			
			if (createProduct == false) {
				CreateResponse response = new CreateResponse("fail: permission is not CREATE PRODUCT");
				return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_ACCEPTABLE);
			} else {
				//Create Product
				Product newProduct = new Product();
				newProduct.setName(objProduct.getName());
				newProduct.setDescription(objProduct.getDescription());
				newProduct.setDetail(objProduct.getDetail());
				newProduct.setCategory(category);
				newProduct.setQuantity(objProduct.getQuantity());
				newProduct.setPrice(objProduct.getPrice());
				newProduct.setAccount(account);
				newProduct.setPicture(objProduct.getPicture());
				
				//Save product
				Product product = productService.addOneProduct(newProduct);
				
				List<String> listPicture = new ArrayList<>();
				String[] arrPicture = product.getPicture().split(",");
				
				for (int i = 0; i < arrPicture.length; i++) {
					listPicture.add(arrPicture[i]);
				}
				
				CreateResponse response = new CreateResponse("success", product.getId());
				
				return new ResponseEntity<CreateResponse>(response, HttpStatus.OK);
			}
		}
	}
	
	//Delete Product ~ Change active product
	@DeleteMapping("{id}")
	public ResponseEntity<MessageResponse> changeActiveProduct(@PathVariable("id") int id){
		Product objProduct = productService.getOneProduct(id);
		if (objProduct == null) {
			MessageResponse msg = new MessageResponse("fail: not found");
			return new ResponseEntity<MessageResponse>(msg, HttpStatus.NOT_FOUND);
		} else {
			//Change active
			objProduct.setActive(0);
			productService.changeActive(objProduct);
			
			MessageResponse msg = new MessageResponse("success");
			return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);
		}
	}
	
	//Get product by id account
	@GetMapping("account/{id}")
	public ResponseEntity<List<ProductDTO>> GetProductByIdAccount(@PathVariable("id") int id){
		List<Product> listProduct = productService.getProductByIdAccount(id);
		if(listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NOT_FOUND);
		}
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		for (Product product : listProduct) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success", product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<List<ProductDTO>>(listProductDTO,HttpStatus.OK);
	}
	
	//Get product by id category
	@GetMapping("category/{id}")
	public ResponseEntity<List<ProductDTO>> GetProductByIdCategory(@PathVariable("id") int id){
		List<Product> listProduct = productService.getProductOfCategory(id);
		
		if(listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NOT_FOUND);
		}
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		for (Product product : listProduct) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success", product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<List<ProductDTO>>(listProductDTO,HttpStatus.OK);
	}
	
}
