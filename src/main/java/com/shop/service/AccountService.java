package com.shop.service;

import com.shop.entities.Account;

public interface AccountService {
	//Add new account
	Account addAccount(Account objAccout);
	
	//Get account by username and password
	Account GetAccountByUsernameAndPassword(String username, String password);
	
	//Get account by id
	Account getAccountById(int id);
}
