package com.shop.service;

import java.util.List;

import com.shop.entities.Transaction;

public interface TransactionService {
	//Get all transaction
	List<Transaction> getAll();
	
	//Get transaction of account
	List<Transaction> getTransactionOfAccount(int id);
}
