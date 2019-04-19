package com.shop.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Account;
import com.shop.entities.Permission;
import com.shop.entities.Profile;
import com.shop.entities.Role;
import com.shop.service.AccountService;
import com.shop.service.PermissionService;
import com.shop.service.ProfileService;
import com.shop.service.RoleService;

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

}
