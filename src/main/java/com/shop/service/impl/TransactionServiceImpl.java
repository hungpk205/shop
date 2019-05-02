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

}
