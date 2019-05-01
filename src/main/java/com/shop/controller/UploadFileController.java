package com.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shop.response.PictureUploadResponse;
import com.shop.response.UploadFileResponse;
import com.shop.service.FileStorageService;

@RestController
@RequestMapping("/api")
public class UploadFileController {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("upload")
	public ResponseEntity<UploadFileResponse> upload(@RequestParam("image") MultipartFile image){
		//Check name
		if (image.isEmpty()) {
			return new ResponseEntity<UploadFileResponse>(HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Upload image
			String fileName = fileStorageService.storeFile(image);
			String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/downloadFile/").path(fileName).toUriString();
			UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadURI, image.getContentType(), image.getSize());
			return new ResponseEntity<UploadFileResponse>(response,HttpStatus.OK);
		}
	}
	
	@PostMapping("uploadMultipart")
	public ResponseEntity<PictureUploadResponse> uploadMultipart (@RequestParam("image") MultipartFile[] image){
		List<UploadFileResponse> list = new ArrayList<>();
		
		String picture = "";
		//Check name
		if (image == null) {
			
			PictureUploadResponse response = new PictureUploadResponse("fail", null);
			return new ResponseEntity<PictureUploadResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			//Upload image
			for (MultipartFile multipartFile : image) {
				String fileName = fileStorageService.storeFile(multipartFile);
				String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/downloadFile/").path(fileName).toUriString();
				picture += fileDownloadURI + ",";
				
				UploadFileResponse response = new UploadFileResponse(fileName, fileDownloadURI, multipartFile.getContentType(), multipartFile.getSize());
				list.add(response);
			}
			
			PictureUploadResponse response = new PictureUploadResponse("success", picture);
			return new ResponseEntity<PictureUploadResponse>(response,HttpStatus.OK);
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
	
}
