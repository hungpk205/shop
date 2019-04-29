package com.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shop.dto.ProductDTO;
import com.shop.entities.Account;
import com.shop.entities.Category;
import com.shop.entities.Permission;
import com.shop.entities.Product;
import com.shop.entities.Role;
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
	public ResponseEntity<List<Product>> getAll(){
		List<Product> listProduct = productService.getAll();
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(listProduct, HttpStatus.OK);
		}
	}
	
	//Get top 10 product
	@GetMapping(value = {"top/{page}", "top"})
	public ResponseEntity<List<Product>> getTop12Pagination(@PathVariable("page") Optional<Integer> page){
		int currentPage = 0;
		if (page.isPresent()) {
			currentPage = page.get();
		}
		
		if (currentPage < 0 || currentPage > (productService.getCountProducts())/2 + 1) {
			currentPage = 0;
		}
		
		int size = 2;
		Pageable pageable = PageRequest.of(currentPage, size);
		List<Product> listProduct = productService.getTop12Pagination(pageable);
		if (listProduct.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Product>>(listProduct,HttpStatus.OK);
		}
		
	}
	
	//Get product by id
	@GetMapping("{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") int id){
		Product objProduct = productService.getOneProduct(id);
		if (objProduct == null) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setMessage("fail: not found");
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.NOT_FOUND);
		} else {
			List<String> listImage = new ArrayList<>();
			String dbImage = objProduct.getPicture();
			String[] arrImage = dbImage.split(",");
			for (int i = 0; i < arrImage.length; i++) {
				listImage.add(arrImage[i]);
			}
			
			ProductDTO productDTO = new ProductDTO("success", objProduct.getId(), objProduct.getName(), objProduct.getCategory().getName(), objProduct.getDescription(), objProduct.getDetail(), listImage, objProduct.getQuantity(), objProduct.getPrice(),objProduct.getCount_buy(), objProduct.getAccount().getUsername(), objProduct.getCreated_at());
			
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
		}
	}
	
	
	
	
	//Add one product
	@PostMapping("add/{id_account}")
	public ResponseEntity<ProductDTO> add(@RequestParam("id_category") int id_category,
										@PathVariable("id_account") int id_account,
										@RequestParam("picture") MultipartFile[] picture,
										@RequestParam("name") String name,
										@RequestParam("description") String description,
										@RequestParam("detail") String detail,
										@RequestParam("quantity") int quantity,
										@RequestParam("price") float price
										
			){
		//Check exit category by id
		Category category = categoryService.getCateogryById(id_category);
		
		if (category == null) {
			ProductDTO response = new ProductDTO();
			response.setMessage("fail category");
			return new ResponseEntity<ProductDTO>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		//Check role account
		Account account = accountService.getAccountById(id_account);
		Set<Role> role = account.getRole();
		String nameRole = "";
		for (Role item : role) {
			nameRole = item.getName();
		}
		if (!nameRole.equals("SHOP OWNER")) {
			ProductDTO response = new ProductDTO();
			response.setMessage("fail role");
			return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
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
				ProductDTO response = new ProductDTO();
				response.setMessage("fail permission");
				return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
			} else {
				//Create Product
				Product newProduct = new Product();
				newProduct.setName(name);
				newProduct.setDescription(description);
				newProduct.setDetail(detail);
				newProduct.setCategory(category);
				newProduct.setQuantity(quantity);
				newProduct.setPrice(price);
				newProduct.setAccount(account);
				
				//Upload picture
				String dbPicture = "";
				for (MultipartFile multipartFile : picture) {
					//Upload file
					String fileName = fileStorageService.storeFile(multipartFile);
					String fileDownload = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/product/downloadFile/").path(fileName).toUriString();
					dbPicture = dbPicture + fileDownload + ",";
				}
				newProduct.setPicture(dbPicture);
				
				Product objProduct = productService.addOneProduct(newProduct);
				
				List<String> listPicture = new ArrayList<>();
				String[] arrPicture = objProduct.getPicture().split(",");
				
				for (int i = 0; i < arrPicture.length; i++) {
					listPicture.add(arrPicture[i]);
				}
				
				ProductDTO productDTO = new ProductDTO("success", objProduct.getId(), objProduct.getName(), objProduct.getCategory().getName(), objProduct.getDescription(), objProduct.getDetail(), listPicture, objProduct.getQuantity(), objProduct.getPrice(), objProduct.getCount_buy(), objProduct.getAccount().getUsername(), objProduct.getCreated_at());
				
				return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
			}
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
	

}
