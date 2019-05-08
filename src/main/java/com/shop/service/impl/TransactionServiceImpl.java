package com.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.entities.Transaction;
import com.shop.repository.TransactionRepository;
import com.shop.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;
	
	@Override
	public List<Transaction> getAll() {
		return transactionRepo.findAll();
	}

	@Override
	public List<Transaction> getTransactionOfAccount(int id) {
		return transactionRepo.getTransactionOfCustomer(id);
	}

	@Override
	public Transaction addTransaction(Transaction objTransaction) {
		return transactionRepo.save(objTransaction);
	}

	@Override
	public Transaction getTransactionById(int id_account, int id_transaction) {
		return transactionRepo.getTransactionByIdTransactionOfAccount(id_account, id_transaction);
	}

}
