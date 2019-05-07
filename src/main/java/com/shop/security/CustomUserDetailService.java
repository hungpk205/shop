package com.shop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.entities.Account;
import com.shop.repository.AccountRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	AccountRepository accountRepo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Account account = accountRepo.findByUsername(username)
				.orElseThrow (() ->
						new UsernameNotFoundException("Not found account with username: " + username));
		
		return UserPrincipal.create(account);
	}
	
	@Transactional
	public UserDetails loadUserById(int id) {
		Account account = accountRepo.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User not found with id " + id));
		return UserPrincipal.create(account);
	}

}
