package com.shop.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shop.entities.Account;
import com.shop.entities.Category;
import com.shop.entities.Product;
import com.shop.response.UploadFileResponse;
import com.shop.service.AccountService;
import com.shop.service.CategoryService;
import com.shop.service.FileStorageService;
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
	
	//Add one product
	@PostMapping("add/{id_account}")
	public ResponseEntity<Product> add(@RequestBody Product objProduct,
										@PathVariable("id_account") int id_account,
										@RequestParam("picture") MultipartFile picture,
										@RequestParam("img1") MultipartFile img1,
										@RequestParam("img2") MultipartFile img2,
										@RequestParam("img3") MultipartFile img3
			){
		//Check exit category by id
		if (categoryService.getCateogry(objProduct.getCategory().getId()) != null) {
			Product newProduct = new Product();
			//Get account by id
			Account account = accountService.getAccountById(id_account);
			Category category = categoryService.getCateogry(objProduct.getCategory().getId());
			if (account == null) {
				//Set account post
				newProduct.setAccount(account);
				newProduct.setCategory(category);
				newProduct.setName(objProduct.getName());
				newProduct.setDescription(objProduct.getDescription());
				newProduct.setDetail(objProduct.getDetail());
				newProduct.setQuantity(objProduct.getQuantity());
				newProduct.setPrice(objProduct.getPrice());
				
				//Upload image
				String fileNamePicture = fileStorageService.storeFile(picture);
				String fileDownloadURIPicture = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNamePicture).toUriString();
				newProduct.setPicture(fileDownloadURIPicture);
				
				if (img1.getOriginalFilename() != null) {
					String fileNameImg1 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg1 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg1).toUriString();
					newProduct.setImg1(fileDownloadURIImg1);
				}
				
				if (img2.getOriginalFilename() != null) {
					String fileNameImg2 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg2 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg2).toUriString();
					newProduct.setImg2(fileDownloadURIImg2);
				}
				
				if (img3.getOriginalFilename() != null) {
					String fileNameImg3 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg3 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg3).toUriString();
					newProduct.setImg3(fileDownloadURIImg3);
				}
				Product productAdded = productService.addOneProduct(newProduct);
				return new ResponseEntity<Product>(productAdded, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}  else {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Add one product
		@PostMapping("upload")
		public UploadFileResponse add(	
											@RequestParam("picture") MultipartFile picture
											/*@RequestParam("img1") MultipartFile img1,
											@RequestParam("img2") MultipartFile img2,
											@RequestParam("img3") MultipartFile img3*/
				){
			
					//List<UploadFileResponse> listUpload = null;
					//Upload image
					String fileNamePicture = fileStorageService.storeFile(picture);
					String fileDownloadURIPicture = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNamePicture).toUriString();
					UploadFileResponse file1 = new UploadFileResponse(fileNamePicture, fileDownloadURIPicture, picture.getContentType(), picture.getSize());
					return file1;
					//listUpload.add(file1);
					
					
					/*String fileNameImg1 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg1 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg1).toUriString();
					UploadFileResponse file2 = new UploadFileResponse(fileNamePicture, fileDownloadURIPicture, img1.getContentType(), img1.getSize());
					listUpload.add(file2);
				
					String fileNameImg2 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg2 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg2).toUriString();
					UploadFileResponse file3 = new UploadFileResponse(fileNamePicture, fileDownloadURIPicture, img2.getContentType(), img2.getSize());
					listUpload.add(file3);
					
					String fileNameImg3 = fileStorageService.storeFile(picture);
					String fileDownloadURIImg3 = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileNameImg3).toUriString();
					UploadFileResponse file4 = new UploadFileResponse(fileNamePicture, fileDownloadURIPicture, img3.getContentType(), img3.getSize());
					listUpload.add(file4);*/
					
					//return listUpload;
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
	
	
	

}
