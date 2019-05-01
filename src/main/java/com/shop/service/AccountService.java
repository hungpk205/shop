package com.shop.service;

import java.util.List;

import com.shop.entities.Account;

public interface AccountService {
	//Add new account
	Account addAccount(Account objAccout);
	
	//Get account by name
	Account getAccountByUsername(String username);
	
	//Get account by username and password
	Account GetAccountByUsernameAndPassword(String username, String password);
	
	//Get account by id
	Account getAccountById(int id);
	
	//Get all account
	List<Account> listAccount();
	
	//Edit account
	Account editAccount(Account objAccount);
	
	//Delete account by id
	void deleteAccount(Account objAccount);
	
	//Exist by id
	boolean CheckExistById(int id);
}
