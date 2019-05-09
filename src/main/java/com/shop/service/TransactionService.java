package com.shop.service;

import java.util.List;

import com.shop.entities.Transaction;

public interface TransactionService {
	//Get all transaction
	List<Transaction> getAll();
	
	//Get transaction of account
	List<Transaction> getTransactionOfAccount(int id);
	
	//Get a transaction of account by id transaction
	Transaction getTransactionById(int id_account, int id_transaction);
	
	//Add a transaction
	Transaction addTransaction(Transaction objTransaction);
	
	//Delete transaction of account
	void DeleteTransactionOfAccount(Transaction transaction);
}
