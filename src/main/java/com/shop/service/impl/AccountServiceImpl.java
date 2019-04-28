package com.shop.service.impl;

import java.util.List;

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

	@Override
	public Account getAccountById(int id) {
		return accountRepo.getOne(id);
	}

	@Override
	public List<Account> listAccount() {
		List<Account> listAccount = accountRepo.findAll();
		return listAccount;
	}

	@Override
	public void deleteAccount(Account objAccount) {
		accountRepo.delete(objAccount);
	}

	@Override
	public Account editAccount(Account objAccount) {
		return accountRepo.save(objAccount);
	}

	@Override
	public Account getAccountByUsername(String username) {
		return accountRepo.getAccountByUsername(username);
	}

}
