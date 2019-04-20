package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Account;
import com.shop.service.AccountService;

@RestController
@RequestMapping("api")
public class LoginController {
	@Autowired
	private AccountService accountService;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("login")
	public ResponseEntity<Account> login(@RequestBody Account objAccount){
		System.out.println(objAccount.getUsername() + " " + objAccount.getPassword());
		Account accountLogin  = accountService.GetAccountByUsernameAndPassword(objAccount.getUsername(), objAccount.getPassword());
		if (accountLogin != null) {
			return new ResponseEntity<Account>(accountLogin, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
