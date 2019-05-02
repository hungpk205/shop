package com.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.entities.Transaction;
import com.shop.service.TransactionService;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	//Get all
	@GetMapping("all")
	public ResponseEntity<List<Transaction>> getAll(){
		List<Transaction> list =  transactionService.getAll();
		return new ResponseEntity<List<Transaction>>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<List<Transaction>> getTransactionOfCustomer(@PathVariable("id") int id){
		List<Transaction> list =  transactionService.getTransactionOfAccount(id);
		return new ResponseEntity<List<Transaction>>(list, HttpStatus.OK);
	}
}
