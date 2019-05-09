package com.shop.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.shop.dto.ProductDTO;
import com.shop.entities.Account;
import com.shop.entities.Category;
import com.shop.entities.Permission;
import com.shop.entities.Product;
import com.shop.entities.Role;
import com.shop.request.ProductRequest;
import com.shop.response.CreateResponse;
import com.shop.response.MessageResponse;
import com.shop.service.AccountService;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("view/all")
	public ResponseEntity<List<ProductDTO>> getAll(){
		List<Product> listProduct = productService.getAll();
		
		List<Product> listProductActive = new ArrayList<>();
		for (Product product : listProduct) {
			if (product.getActive() == 1) {
				listProductActive.add(product);
			}
		}
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		
		for (Product product : listProductActive) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
			
		}
		
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
		}
	}
	
	//Top new product
	@GetMapping("view/top")
	public ResponseEntity<?> getTop12Product(){
		List<Product> listProduct = productService.getTop12Product();
		
		List<ProductDTO> listProductDTO = new ArrayList<>();
		
		for (Product product : listProduct) {
			List<String> listImage = new ArrayList<>();
			String[] arrImage = product.getPicture().split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
			
		}
		
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<ProductDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<ProductDTO>>(listProductDTO, HttpStatus.OK);
		}
	}
	
	
	//Top buy product
		@GetMapping("view/top-buy")
		public ResponseEntity<?> getTop12ProductBuy(){
			List<Product> listProduct = productService.getTop12ProductBuy();
			
			List<ProductDTO> listProductDTO = new ArrayList<>();
			
			for (Product product : listProduct) {
				List<String> listImage = new ArrayList<>();
				String[] arrImage = product.getPicture().split(",");
				for (int i = 0; i < arrImage.length; i++) {
					listImage.add(arrImage[i]);
				}
				String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
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
	@GetMapping("view/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") int id){
		//Check product
		if (!productService.CheckExistProduct(id)) {
			MessageResponse response = new MessageResponse("Not exist product id " + id);
			return new ResponseEntity<MessageResponse>(response, HttpStatus.NOT_FOUND);
			
		} else {
			Product objProduct = productService.getOneProduct(id);
			
			List<String> listImage = new ArrayList<>();
			String dbImage = objProduct.getPicture();
			String[] arrImage = dbImage.split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			String time = new SimpleDateFormat("dd/MM/yyyy").format(objProduct.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success",objProduct.getId(), objProduct.getName(), objProduct.getCategory().getName(), objProduct.getDescription(), objProduct.getDetail(), listImage, objProduct.getQuantity(), objProduct.getPrice(),objProduct.getCount_buy(), objProduct.getAccount().getUsername(), time);
			
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
		}
	}
	
	//Get product of shop
	@GetMapping("shop")
	public ResponseEntity<List<ProductDTO>> GetProductByIdAccount(Principal user){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		
		List<Product> listProduct = productService.getProductByIdAccount(accountLogin.getId());
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
			String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success", product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<List<ProductDTO>>(listProductDTO,HttpStatus.OK);
	}
	
	//Get product by id category
	@GetMapping("view/category/{id}")
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
			String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success", product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<List<ProductDTO>>(listProductDTO,HttpStatus.OK);
	}
	
	//Get product in category by id category for shop
	@GetMapping("shop/category/{idCat}")
	public ResponseEntity<List<ProductDTO>> GetProductByIdCategory(Principal user, @PathVariable("idCat") int idCat){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		List<Product> listProduct = productService.getProductOfCategoryShop(idCat, accountLogin.getId());
		
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
			String time = new SimpleDateFormat("dd/MM/yyyy").format(product.getCreated_at());
			ProductDTO productDTO = new ProductDTO("success", product.getId(), product.getName(), product.getCategory().getName(), product.getDescription(), product.getDetail(), listImage, product.getQuantity(), product.getPrice(), product.getCount_buy(), product.getAccount().getUsername(), time);
			listProductDTO.add(productDTO);
		}
		
		return new ResponseEntity<List<ProductDTO>>(listProductDTO,HttpStatus.OK);
	}
	
	

	//Add one product
	@PostMapping("add")
	public ResponseEntity<CreateResponse> add(Principal user ,@Valid @RequestBody ProductRequest objProduct){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		//Check role
		boolean isShop = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("SHOP OWNER")) {
				isShop = true;
				break;
			}
		}
		
		//Check permission
		boolean isPermission = false;
		Set<Permission> permissions = accountLogin.getPermission();
		for (Permission permission : permissions) {
			if (permission.getName().equals("CREATE_PRODUCT")) {
				isPermission = true;
				break;
			}
		}
		
		if (!isShop && !isPermission) {
			CreateResponse response = new CreateResponse("false");
			return new ResponseEntity<CreateResponse>(response, HttpStatus.FORBIDDEN);
		}

		//Check exit category by id
		if (!categoryService.CheckExitCategoryById(objProduct.getId_category())) {
			CreateResponse response = new CreateResponse("fail: not exist category id " + objProduct.getId_category());
			return new ResponseEntity<CreateResponse>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		Category category = categoryService.getCateogryById(objProduct.getId_category());
				
		//Create Product
		Product newProduct = new Product();
		newProduct.setName(objProduct.getName());
		newProduct.setDescription(objProduct.getDescription());
		newProduct.setDetail(objProduct.getDetail());
		newProduct.setCategory(category);
		newProduct.setQuantity(objProduct.getQuantity());
		newProduct.setPrice(objProduct.getPrice());
		newProduct.setAccount(accountLogin);
		newProduct.setPicture(objProduct.getPicture());
		newProduct.setActive(1);
		
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		newProduct.setCreated_at(created_at);
		
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
	
	//Edit product
	@PutMapping("{id}")
	public ResponseEntity<MessengerUtils> editProduct(Principal user ,@PathVariable("id") int id_product, @RequestBody ProductRequest objProduct){
		
		if(!productService.CheckExistProduct(id_product)) {
			MessengerUtils msg = new MessengerUtils(false,"Not found product id " + id_product);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_FOUND);
		} else {
			//Check product is of account
			Product product = productService.getOneProduct(id_product);
			
			if (!product.getAccount().getUsername().equals(user.getName())) {
				MessengerUtils response = new MessengerUtils(false, "Not have permission");
				return new ResponseEntity<MessengerUtils>(response, HttpStatus.FORBIDDEN);
			}
			
			//Change data
			if (objProduct.getName() != null) {
				if (!objProduct.getName().equals("")) {
					product.setName(objProduct.getName());
				}
			}
			
			if (objProduct.getDescription() != null) {
				if (!objProduct.getDescription().equals("")) {
					product.setDescription(objProduct.getDescription());
				}
			}
			
			if (objProduct.getDetail() != null) {
				if (!objProduct.getDetail().equals("")) {
					product.setDetail(objProduct.getDetail());
				}
				
			}
			
			if (objProduct.getPicture() != null) {
				if (!objProduct.getPicture().equals("")) {
					product.setPicture(objProduct.getPicture());
				}
			}
			
			if (objProduct.getQuantity() >= 0) {
				product.setQuantity(objProduct.getQuantity());
			}
			
			if (objProduct.getPrice() > 0) {
				product.setPrice(objProduct.getPrice());
			}
			if (objProduct.getId_category() > 0) {
				if (categoryService.CheckExitCategoryById(objProduct.getId_category())) {
					Category objCategory = categoryService.getCateogryById(objProduct.getId_category());
					product.setCategory(objCategory);	
				} else {
					MessengerUtils response = new MessengerUtils(false,"Not found category id " + objProduct.getId_category());
					return new ResponseEntity<MessengerUtils>(response, HttpStatus.NOT_FOUND);
				}
			}
			
			//Save product
			productService.editProduct(product);
			
			MessengerUtils msg = new MessengerUtils(true, "Edited");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
			
		}
	}
	
	
	//Delete Product ~ Change active product
	@DeleteMapping("{id}")
	public ResponseEntity<MessengerUtils> changeActiveProduct(Principal user ,@PathVariable("id") int id){
		if (!productService.CheckExistProduct(id)) {
			MessengerUtils msg = new MessengerUtils(false,"Not found product id " + id);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_FOUND);
		}
		Product objProduct = productService.getOneProduct(id);
		//Check product is of account
		Product product = productService.getOneProduct(id);
		
		if (!product.getAccount().getUsername().equals(user.getName())) {
			MessengerUtils response = new MessengerUtils(false, "Not have permission");
			return new ResponseEntity<MessengerUtils>(response, HttpStatus.FORBIDDEN);
		}
		
		//Change active
		objProduct.setActive(0);
		productService.changeActive(objProduct);
		
		MessengerUtils msg = new MessengerUtils(true, "Deleted product id " + id);
		return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
	}
	
}
