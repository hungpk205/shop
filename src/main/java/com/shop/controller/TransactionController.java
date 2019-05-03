package com.shop.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.BuyerDTO;
import com.shop.dto.OrderDTO;
import com.shop.dto.OrderInformation;
import com.shop.dto.SellerDTO;
import com.shop.dto.TransactionDTO;
import com.shop.entities.Order;
import com.shop.entities.Transaction;
import com.shop.service.TransactionService;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	//Get all
	@GetMapping("all")
	public ResponseEntity<List<TransactionDTO>> getAll(){
		List<Transaction> list =  transactionService.getAll();
		
		List<TransactionDTO> listDTO = new ArrayList<>();
		if (list.size() > 0) {
			for (Transaction transaction : list) {
				BuyerDTO buyer = new BuyerDTO();
				
				if (transaction.getAccount()!= null) {
					buyer = new BuyerDTO(transaction.getAccount().getProfile().getFullname(), transaction.getAccount().getProfile().getEmail(), transaction.getAccount().getProfile().getPhone(), transaction.getAccount().getProfile().getAddress());
				} else {
					buyer = new BuyerDTO(transaction.getUser_name(), transaction.getUser_email(), transaction.getUser_phone(), transaction.getAddress());
				}
				
				Set<Order> listOrder = transaction.getOrder();
				List<OrderInformation> listOrderInfor = new ArrayList<>();
				
				for (Order order : listOrder) {
					SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getId(), order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
					
					OrderInformation orderInfor = new OrderInformation(order.getId(), seller ,order.getProduct().getName(), order.getQuantity(), order.getAmount(), order.getStatus());
					
					listOrderInfor.add(orderInfor);
				}
				
				String created_at = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss").format(transaction.getCreated_at());
				
				TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), buyer, listOrderInfor, transaction.getAmount(), transaction.getPayment().getName(), transaction.getPayment_infor(), transaction.getMessage(), created_at, transaction.getStatus());
				listDTO.add(transactionDTO);
			}
			
		}
		
		
		
		return new ResponseEntity<List<TransactionDTO>>(listDTO, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<List<Transaction>> getTransactionOfCustomer(@PathVariable("id") int id){
		List<Transaction> list =  transactionService.getTransactionOfAccount(id);
		return new ResponseEntity<List<Transaction>>(list, HttpStatus.OK);
	}
}
