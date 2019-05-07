package com.shop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.AccountDTO;
import com.shop.entities.Account;
import com.shop.entities.Permission;
import com.shop.entities.Role;
import com.shop.payload.LoginRequest;
import com.shop.response.JwtAuthenticationResponse;
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
	
	@PostMapping("login-1")
	public ResponseEntity<AccountDTO> login(@RequestBody Account objAccount){
		//System.out.println(objAccount.getUsername() + " " + objAccount.getPassword());
		Account accountLogin  = accountService.GetAccountByUsernameAndPassword(objAccount.getUsername(), objAccount.getPassword());
		
		if (accountLogin != null) {
			if (accountLogin.getStatus() == 0) {
				AccountDTO accountDTO = new AccountDTO();
				return new ResponseEntity<AccountDTO>(accountDTO,HttpStatus.NOT_ACCEPTABLE);
			} else {
				AccountDTO accountDTO = new AccountDTO(accountLogin.getId(), accountLogin.getUsername(), accountLogin.getStatus(), accountLogin.getProfile());
				for (Role item : accountLogin.getRole()) {
					accountDTO.setRole(item.getName());
				}
				List<String> permission = new ArrayList<>();
				for (Permission item : accountLogin.getPermission()) {
					permission.add(item.getName());
				}
				accountDTO.setPermission(permission);
				
				return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
			}
			
		} else {
			AccountDTO accountDTO = new AccountDTO();
			return new ResponseEntity<AccountDTO>(accountDTO,HttpStatus.NOT_FOUND);
		}
	}
	
	//
	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
				
	}
	
}
