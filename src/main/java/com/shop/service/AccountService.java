package com.shop.service;

import java.util.List;

import com.shop.entities.Account;

public interface AccountService {
	//Add new account
	Account addAccount(Account objAccout);
	
	//Get account by username and password
	Account GetAccountByUsernameAndPassword(String username, String password);
	
	//Get account by id
	Account getAccountById(int id);
	
	//Get all account
	List<Account> listAccount();
	
	//Delete account by id
	void deleteAccount(Account objAccount);
}
