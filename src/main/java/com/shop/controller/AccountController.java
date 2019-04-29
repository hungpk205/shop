package com.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.shop.dto.AccountDTO;
import com.shop.entities.Account;
import com.shop.entities.Permission;
import com.shop.entities.Profile;
import com.shop.entities.Role;
import com.shop.response.MessageResponse;
import com.shop.response.RegisterResponse;
import com.shop.service.AccountService;
import com.shop.service.FileStorageService;
import com.shop.service.PermissionService;
import com.shop.service.ProfileService;
import com.shop.service.RoleService;
import com.shop.utils.MessengerUtils;

@RestController
@RequestMapping("api/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ProfileService profileService;
	
	
	//Add
	@PostMapping("add")
	public ResponseEntity<RegisterResponse> add(@RequestBody Account objAccount){
			
		Account newAccount = new Account(objAccount);
		//Check exist username
		if (accountService.getAccountByUsername(newAccount.getUsername()) != null) {
			RegisterResponse response = new RegisterResponse("fail: exist this username " + newAccount.getUsername());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		Set<Role> role = objAccount.getRole();
		for (Role item: role) {
			newAccount.getRole().add(roleService.getRoleById(item.getId()));
		}
		
		//Check role
		boolean check = true;
		for (Role item : newAccount.getRole()) {
			if (item.getName().equals("ADMIN") || item.getName().equals("CUSTOMER")) {
				check = false;
				break;
			}
		}
		//Check permission SHOP OWNER
		boolean checkPermission = false;
		for (Role item : newAccount.getRole()) {
			if (item.getName().equals("SHOP OWNER")) {
				checkPermission = true;
				break;
			}
		}
		if (check == false) {
			RegisterResponse response = new RegisterResponse("fail");
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			if (checkPermission == false) {
				RegisterResponse response = new RegisterResponse("fail");
				return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
			} else {
				Set<Permission> listPermission = objAccount.getPermission();
				for (Permission item: listPermission) {
					newAccount.getPermission().add(permissionService.getPermissionById(item.getId()));
				}
				
				Account account = accountService.addAccount(newAccount);
				RegisterResponse response = new RegisterResponse("success", account.getId());
				return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
			}
		}
		
	}
	
	@PostMapping("register")
	public ResponseEntity<RegisterResponse> register(@RequestParam("username") String username, @RequestParam("password") String password){
		if (accountService.getAccountByUsername(username) == null) {
			Account account = new Account(null, username, password, 1);
			
			//Role role = roleService.getRoleByName("CUSTOMER");
			
			Role role = roleService.getRoleByName("CUSTOMER");
			account.getRole().add(role);
			account.setProfile(new Profile());
			account.setPermission(null);
			Account accountSave =  accountService.addAccount(account);
			
			
			RegisterResponse response = new RegisterResponse("success", accountSave.getId());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
			
		} else {
			RegisterResponse response = new RegisterResponse("fail");
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	//Get List Account
	@GetMapping("all")
	public ResponseEntity<List<AccountDTO>> getAll(){
		List<Account> listAccount = accountService.listAccount();
		
		List<AccountDTO> listAccountDTO = new ArrayList();
		for (Account account : listAccount) {
			AccountDTO objAccountDTO = new AccountDTO(account.getUsername(), account.getStatus(), account.getProfile());
			for(Role role :account.getRole()) {
				objAccountDTO.setRole(role.getName());
			}
			
			List<String> listPermission = new ArrayList<>();
			for(Permission item :account.getPermission()) {
				listPermission.add(item.getName());
			}
			objAccountDTO.setPermission(listPermission);
			
			listAccountDTO.add(objAccountDTO);
		}
		
		if (listAccountDTO.isEmpty()) {
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<AccountDTO>>(listAccountDTO, HttpStatus.OK);
		}
	}
	
	//Get information account
	@GetMapping("{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") int id){
		Account account = accountService.getAccountById(id);
		if (account != null) {
			AccountDTO accountDTO = new AccountDTO(account.getUsername(), account.getStatus(), account.getProfile());
			for (Role role : account.getRole()) {
				accountDTO.setRole(role.getName());
			}
			
			List<String> listPermission = new ArrayList<>();
			for (Permission item : account.getPermission()) {
				listPermission.add(item.getName());
			}
			accountDTO.setPermission(listPermission);
			
			return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
		
	}
	
	//Edit Account
	@PutMapping("{id}")
	public ResponseEntity<MessageResponse> editAccount(@PathVariable("id") int id,
			@RequestParam("fullname") String fullname,
			@RequestParam(value = "avatar", required = false) MultipartFile avatar,
			@RequestParam("email") String email,
			@RequestParam("phone") String phone,
			@RequestParam("address") String address){
		//Get account by id
		Account account = accountService.getAccountById(id);
		
		String avatarOld = account.getProfile().getAvatar();
		
		Profile objProfile = new Profile();
		objProfile.setId(account.getProfile().getId());
		objProfile.setFullname(fullname);
		objProfile.setEmail(email);;
		objProfile.setPhone(phone);;
		objProfile.setAddress(address);
		//Check chosen file
		if( avatar != null) {
			//Upload file
			String fileName = fileStorageService.storeFile(avatar);
			String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/account/downloadFile/").path(fileName).toUriString();
			objProfile.setAvatar(fileDownloadURI);
			
			account.setProfile(objProfile);
			accountService.editAccount(account);
			
			MessageResponse msg = new MessageResponse("success");
			return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);
		} else {
			objProfile.setAvatar(avatarOld);
			account.setProfile(objProfile);
			accountService.editAccount(account);
			
			MessageResponse msg = new MessageResponse("success");
			return new ResponseEntity<MessageResponse>(msg, HttpStatus.OK);
		}
		
		
	}
	//Link image
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile (@PathVariable String fileName, HttpServletRequest request){
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			//logger.info("Could not determine file type.");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		//return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
		return new ResponseEntity<Resource>(resource, HttpStatus.OK);
	}
	
	
	
	//Delete account
	@DeleteMapping("{id}")
	public ResponseEntity<MessengerUtils> delete(@PathVariable("id") int id, @RequestBody Account accountLogin){
		//Get account by id
		Account objAccount = accountService.getAccountById(id);
		
		//Check exist account
		if (objAccount == null) {
			MessengerUtils msg = new MessengerUtils("false", "Not exist account id " + id);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_ACCEPTABLE);
		}
		
		//Check role of accountLogin
		if (roleService.getRoleById(accountLogin.getId()).getName().equals("ADMIN")) {
			//Delete account profile
			profileService.deleteProfile(objAccount.getProfile());
			
			//Delete 
			
			//Delete account
			accountService.deleteAccount(objAccount);
			
			
			MessengerUtils msg = new MessengerUtils("true", "Deleted account id " + id);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
		} else {
			MessengerUtils msg = new MessengerUtils("fasle", "Do not have permissions");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
		}
		
		
	}

}
