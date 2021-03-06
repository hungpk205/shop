package com.shop.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.AccountDTO;
import com.shop.entities.Account;
import com.shop.entities.Permission;
import com.shop.entities.Product;
import com.shop.entities.Profile;
import com.shop.entities.Role;
import com.shop.entities.Transaction;
import com.shop.request.RegisterRequest;
import com.shop.response.RegisterResponse;
import com.shop.service.AccountService;
import com.shop.service.CartService;
import com.shop.service.PermissionService;
import com.shop.service.ProductService;
import com.shop.service.RoleService;
import com.shop.service.TransactionService;
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
	private ProductService productService;
	@Autowired
	private PasswordEncoder encoderPassword;
	@Autowired
	private CartService cartService;
	@Autowired
	private TransactionService transactionService;
	
	
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
	
	//Register mobile app
	
	@PostMapping("register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
		if (accountService.getAccountByUsername(registerRequest.getUsername()) == null) {
			
			//Encode password
			String encodePassword = encoderPassword.encode(registerRequest.getPassword());
			
			Account account = new Account(null, registerRequest.getUsername(), encodePassword, 1);
			
			Role role = roleService.getRoleByName("CUSTOMER");
			account.getRole().add(role);
			account.setProfile(new Profile());
			account.setPermission(null);
			Account accountSave =  accountService.addAccount(account);
			
			Profile profile = accountSave.getProfile();
			profile.setFullname(registerRequest.getFullname());
			profile.setEmail(registerRequest.getEmail());
			accountService.editAccount(accountSave);
			
			RegisterResponse response = new RegisterResponse("success", accountSave.getId());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
			
		} else {
			RegisterResponse response = new RegisterResponse("fail, existed username " + registerRequest.getUsername());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Register shop
	@PostMapping("register/shop")
	public ResponseEntity<RegisterResponse> registerShop(@Valid @RequestBody RegisterRequest registerRequest, BindingResult br){
		if (br.hasErrors()) {
			RegisterResponse response = new RegisterResponse("fail, required fullname, email, username, password");
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (accountService.getAccountByUsername(registerRequest.getUsername()) == null) {
			//Encode password
			String password = encoderPassword.encode(registerRequest.getPassword());
			
			Account account = new Account(null, registerRequest.getUsername(), password, 1);
			
			
			Role role = roleService.getRoleByName("SHOP OWNER");
			account.getRole().add(role);
			account.setProfile(new Profile());
			
			//Set permission
			Permission p1 = permissionService.getPermissionByName("CREATE_PRODUCT");
			Permission p2 = permissionService.getPermissionByName("EDIT_PRODUCT");
			Permission p3 = permissionService.getPermissionByName("DEL_PRODUCT");
			Permission p4 = permissionService.getPermissionByName("VIEW_ORDER");
			
			Set<Permission> permission = new HashSet<>();
			permission.add(p1);
			permission.add(p2);
			permission.add(p3);
			permission.add(p4);
			
			account.setPermission(permission);
			Account accountSave =  accountService.addAccount(account);
			
			Profile profile = accountSave.getProfile();
			profile.setFullname(registerRequest.getFullname());
			profile.setEmail(registerRequest.getEmail());
			accountService.editAccount(accountSave);
			
			RegisterResponse response = new RegisterResponse("success", accountSave.getId());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.OK);
			
		} else {
			RegisterResponse response = new RegisterResponse("fail, existed username " + registerRequest.getUsername());
			return new ResponseEntity<RegisterResponse>(response, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	//Get List Account
	@GetMapping("all")
	public ResponseEntity<?> getAll(Principal user){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		//Check role
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessengerUtils msg = new MessengerUtils(false, "Not have permission");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.FORBIDDEN);
		}
		
		List<Account> listAccount = accountService.listAccount();
		
		List<AccountDTO> listAccountDTO = new ArrayList<>();
		for (Account account : listAccount) {
			int product_cart = cartService.getCartOfAccount(account.getId()).size();
			AccountDTO objAccountDTO = new AccountDTO(account.getId(), account.getUsername(), account.getStatus(), account.getProfile(),product_cart);
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
	public ResponseEntity<?> getAccount(Principal user,@PathVariable("id") int id){
		//Get account current
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		//Admin and current user only see information
		boolean isAdmin = false;
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		boolean isAccount = false;
		if (accountLogin.getId().equals(id)) {
			isAccount = true;
		}
		if (!isAdmin && !isAccount) {
			MessengerUtils msg = new MessengerUtils(false, "Not have permission");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.FORBIDDEN);
		}
		
		Account account = accountService.getAccountById(id);
		if (account != null) {
			int product_cart = cartService.getCartOfAccount(account.getId()).size();
			AccountDTO accountDTO = new AccountDTO(account.getId(),account.getUsername(), account.getStatus(), account.getProfile(), product_cart);
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
	
	//Get current account
	@GetMapping("current")
	public ResponseEntity<AccountDTO> getCurrentAccount(Principal user){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		int product_cart = cartService.getCartOfAccount(accountLogin.getId()).size();
		
		AccountDTO accountDTO = new AccountDTO(accountLogin.getId(),accountLogin.getUsername(), accountLogin.getStatus(), accountLogin.getProfile(),product_cart);
		
		Set<Role> roles = accountLogin.getRole();
		for (Role role : roles) {
			accountDTO.setRole(role.getName());
		}
		Set<Permission> permissions = accountLogin.getPermission();
		List<String> listPermission = new ArrayList<>();
		for (Permission item : permissions) {
			listPermission.add(item.getName());
		}
		accountDTO.setPermission(listPermission);
		
		//Get number product in cart
		accountDTO.setProduct_cart(cartService.getCartOfAccount(accountLogin.getId()).size());
		
		
		return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
	}
	
	//Edit Account Profile
	@PutMapping("edit")
	public ResponseEntity<MessengerUtils> editAccount(Principal user,
			@RequestBody Profile objProfile
			){
		
		Account accountCurrent = accountService.getAccountByUsername(user.getName());
		
		//Get account by id
		Account account = accountService.getAccountById(accountCurrent.getId());
		
		Profile profile = accountCurrent.getProfile();
		
		if (objProfile.getFullname() != null && !objProfile.getFullname().equals("")) {
			profile.setFullname(objProfile.getFullname());
		}
		if (objProfile.getAvatar() != null && !objProfile.getAvatar().equals("")) {
			profile.setAvatar(objProfile.getAvatar());
		}
		if (objProfile.getEmail() != null && !objProfile.getEmail().equals("")) {
			profile.setEmail(objProfile.getEmail());
		}
		if (objProfile.getPhone() != null && !objProfile.getPhone().equals("")) {
			profile.setPhone(objProfile.getPhone());
		}
		if (objProfile.getAddress() != null && !objProfile.getAddress().equals("")) {
			profile.setAddress(objProfile.getAddress());
		}
		
		account.setProfile(profile);
		accountService.editAccount(account);
		
		MessengerUtils msg = new MessengerUtils(true, "Edited account id " + accountCurrent.getId());
		return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
		
	}
	
	//Delete account
	@DeleteMapping("{id}")
	public ResponseEntity<MessengerUtils> delete(Principal user ,@PathVariable("id") int id){
		//Check role user
		Account accountCurrent = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountCurrent.getRole();
		boolean isAdmin = false;
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessengerUtils msg = new MessengerUtils(false, "Not have role");
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.BAD_REQUEST);
		}
		
		//Get account by id
		Account objAccount = accountService.getAccountById(id);
		
		//Check exist account
		if (objAccount == null) {
			MessengerUtils msg = new MessengerUtils(false, "Not exist account id " + id);
			return new ResponseEntity<MessengerUtils>(msg, HttpStatus.NOT_ACCEPTABLE);
		}	
		//Delete product created by account
		List<Product> listProduct = productService.getProductByIdAccount(id);
		for (Product product : listProduct) {
			productService.changeActive(product);
		}
		
		//Delete cart
		cartService.DeleteCartOfAccount(id);
		//Delete transaction
		List<Transaction> listTransaction = transactionService.getTransactionOfAccount(id);
		for (Transaction transaction : listTransaction) {
			transactionService.DeleteTransactionOfAccount(transaction);
		}
		
		//Delete account
		accountService.deleteAccount(objAccount);
		
		MessengerUtils msg = new MessengerUtils(true, "Deleted account id " + id);
		return new ResponseEntity<MessengerUtils>(msg, HttpStatus.OK);
		
	}

}
