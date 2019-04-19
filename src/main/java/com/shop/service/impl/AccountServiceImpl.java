package com.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Account;
import com.shop.repository.AccountRepository;
import com.shop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepo;
	
	@Override
	public Account GetAccountByUsernameAndPassword(String username, String password) {
		Account accountLogin =  accountRepo.GetAccountByUsernameAndPassword(username, password);
		return accountLogin;
	}

	@Override
	public Account addAccount(Account objAccout) {
		return accountRepo.save(objAccout);
	}

}
