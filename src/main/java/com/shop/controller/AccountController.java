package com.shop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.AccountDTO;
import com.shop.entities.Account;
import com.shop.entities.Permission;
import com.shop.entities.Role;
import com.shop.service.AccountService;
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
	private ProfileService profileService;
	
	
	//Register
	@PostMapping("register")
	public ResponseEntity<Account> register(@RequestBody Account objAccount
			){
		Account newAccount = new Account(objAccount);
		
		Set<Role> role = objAccount.getRole();
		for (Role item: role) {
			newAccount.getRole().add(roleService.getRoleById(item.getId()));
		}
		
		Set<Permission> listPermission = objAccount.getPermission();
		for (Permission item: listPermission) {
			newAccount.getPermission().add(permissionService.getPermissionById(item.getId()));
		}
		
		Account account = accountService.addAccount(newAccount);
		
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	//Get List Account
	@GetMapping("all")
	public ResponseEntity<List<AccountDTO>> getAll(){
		List<Account> listAccount = accountService.listAccount();
		
		List<AccountDTO> listAccountDTO = new ArrayList();
		for (Account account : listAccount) {
			AccountDTO objAccountDTO = new AccountDTO(account.getId(), account.getUsername(), account.getStatus(), account.getProfile());
			for(Role role :account.getRole()) {
				objAccountDTO.setRole(role.getName());
			}
			objAccountDTO.setPermission(account.getPermission());
			listAccountDTO.add(objAccountDTO);
		}
		
		if (listAccountDTO.isEmpty()) {
			return new ResponseEntity<List<AccountDTO>>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<AccountDTO>>(listAccountDTO, HttpStatus.OK);
		}
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
