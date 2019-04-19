package com.shop.service;

import com.shop.entities.Account;

public interface AccountService {
	
	Account addAccount(Account objAccout);
	
	Account GetAccountByUsernameAndPassword(String username, String password);
}
