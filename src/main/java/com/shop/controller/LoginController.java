package com.shop.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Account;
import com.shop.entities.Role;
import com.shop.request.LoginRequest;
import com.shop.response.JwtAuthenticationResponse;
import com.shop.response.MessageResponse;
import com.shop.security.JwtTokenProvider;
import com.shop.service.AccountService;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api")
public class LoginController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	//Login admin
	@PostMapping("login-admin")
	public ResponseEntity<?> authenticateAdmin(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Account accountLogin = accountService.getAccountByUsername(loginRequest.getUsername());
		Set<Role> roles = accountLogin.getRole();
		boolean isAdmin = false;
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
			}
		}
		if (authentication != null && isAdmin) {
			String jwt = tokenProvider.generateToken(authentication);
			return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		}
		
		
		return ResponseEntity.ok(new MessageResponse("fail"));
				
	}
	
	//Login shop
		@PostMapping("login-shop")
		public ResponseEntity<?> authenticateShop(@Valid @RequestBody LoginRequest loginRequest){
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
					);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Account accountLogin = accountService.getAccountByUsername(loginRequest.getUsername());
			Set<Role> roles = accountLogin.getRole();
			boolean isAdmin = false;
			for (Role role : roles) {
				if (role.getName().equals("SHOP OWNER")) {
					isAdmin = true;
				}
			}
			if (authentication != null && isAdmin) {
				String jwt = tokenProvider.generateToken(authentication);
				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
			}
			
			
			return ResponseEntity.ok(new MessageResponse("fail"));
					
		}
		
		
		//Login customer
		@PostMapping("login-customer")
		public ResponseEntity<?> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest){
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
					);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Account accountLogin = accountService.getAccountByUsername(loginRequest.getUsername());
			Set<Role> roles = accountLogin.getRole();
			boolean isAdmin = false;
			for (Role role : roles) {
				if (role.getName().equals("CUSTOMER")) {
					isAdmin = true;
				}
			}
			if (authentication != null && isAdmin) {
				String jwt = tokenProvider.generateToken(authentication);
				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
			}
			
			
			return ResponseEntity.ok(new MessageResponse("fail"));
					
		}
	
}
