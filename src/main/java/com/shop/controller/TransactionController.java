package com.shop.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.dto.BuyerDTO;
import com.shop.dto.OrderInformation;
import com.shop.dto.SellerDTO;
import com.shop.dto.TransactionDTO;
import com.shop.entities.Account;
import com.shop.entities.Order;
import com.shop.entities.Payment;
import com.shop.entities.Product;
import com.shop.entities.Transaction;
import com.shop.response.CreateResponse;
import com.shop.response.MessageResponse;
import com.shop.service.AccountService;
import com.shop.service.OrderService;
import com.shop.service.PaymentService;
import com.shop.service.ProductService;
import com.shop.service.TransactionService;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("alll")
	public ResponseEntity<List<Transaction>> getAlll(){
		List<Transaction> list =  transactionService.getAll();
		return new ResponseEntity<List<Transaction>>(list,HttpStatus.OK);
		
	}
	
	
	//Get all
	@GetMapping("all")
	public ResponseEntity<?> getAll(Principal user){
		//Admin only se
		
		List<Transaction> list =  transactionService.getAll();
		
		List<TransactionDTO> listDTO = new ArrayList<>();
		if (list.size() > 0) {
			for (Transaction transaction : list) {
				BuyerDTO buyer = new BuyerDTO();
				
				if (transaction.getAccount()!= null) {
					buyer = new BuyerDTO(transaction.getAccount().getProfile().getFullname(), transaction.getAccount().getProfile().getPhone(), transaction.getAccount().getProfile().getAddress());
				} else {
					buyer = new BuyerDTO(transaction.getUser_name(), transaction.getUser_phone(), transaction.getAddress());
				}
				
				Set<Order> listOrder = transaction.getOrder();
				List<OrderInformation> listOrderInfor = new ArrayList<>();
				
				for (Order order : listOrder) {
					SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getId(), order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
					
					OrderInformation orderInfor = new OrderInformation(order.getId(), seller ,order.getProduct().getName(), order.getQuantity(), order.getAmount(), order.getStatus());
					
					listOrderInfor.add(orderInfor);
				}
				
				String created_at = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCreated_at());
				
				TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), buyer, listOrderInfor, transaction.getAmount(), transaction.getPayment().getName(), transaction.getPayment_infor(), transaction.getMessage(), created_at, transaction.getStatus());
				listDTO.add(transactionDTO);
			}
			
		}
		
		
		
		return new ResponseEntity<List<TransactionDTO>>(listDTO, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<List<TransactionDTO>> getTransactionOfCustomer(@PathVariable("id") int id){
		List<Transaction> list =  transactionService.getTransactionOfAccount(id);
		
		
		List<TransactionDTO> listDTO = new ArrayList<>();
		if (list.size() > 0) {
			for (Transaction transaction : list) {
				BuyerDTO buyer = new BuyerDTO();
				
				if (transaction.getAccount()!= null) {
					buyer = new BuyerDTO(transaction.getAccount().getProfile().getFullname(), transaction.getAccount().getProfile().getPhone(), transaction.getAccount().getProfile().getAddress());
				} else {
					buyer = new BuyerDTO(transaction.getUser_name(), transaction.getUser_phone(), transaction.getAddress());
				}
				
				Set<Order> listOrder = transaction.getOrder();
				List<OrderInformation> listOrderInfor = new ArrayList<>();
				
				for (Order order : listOrder) {
					SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getId(), order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
					
					OrderInformation orderInfor = new OrderInformation(order.getId(), seller ,order.getProduct().getName(), order.getQuantity(), order.getAmount(), order.getStatus());
					
					listOrderInfor.add(orderInfor);
				}
				
				String created_at = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCreated_at());
				
				TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), buyer, listOrderInfor, transaction.getAmount(), transaction.getPayment().getName(), transaction.getPayment_infor(), transaction.getMessage(), created_at, transaction.getStatus());
				listDTO.add(transactionDTO);
			}
			
		}
		
		return new ResponseEntity<List<TransactionDTO>>(listDTO, HttpStatus.OK);
	}
	
	//Add transaction
	@PostMapping("add")
	public ResponseEntity<CreateResponse> add(@RequestBody Transaction objTransaction){
		
		Transaction newTransaction = new Transaction();
		//Get buyer
		//Check account
		if (objTransaction.getAccount()!= null) {
			if (accountService.CheckExistById(objTransaction.getAccount().getId())) {
				Account buyer = accountService.getAccountById(objTransaction.getAccount().getId());
				newTransaction.setAccount(buyer);
			} 
		}
		
		newTransaction.setUser_name(objTransaction.getUser_name());
		newTransaction.setUser_phone(objTransaction.getUser_phone());
		newTransaction.setAddress(objTransaction.getAddress());
		newTransaction.setAmount(objTransaction.getAmount());
		newTransaction.setPayment_infor(objTransaction.getPayment_infor());
		newTransaction.setMessage(objTransaction.getMessage());
		
		Payment payment = paymentService.getPaymentById(objTransaction.getPayment().getId());
		newTransaction.setPayment(payment);
		
		//List product
		Set<Order> listOrder = objTransaction.getOrder();
		Set<Order> listOrderAdd = new HashSet<>();
		//Get product
		for (Order order : listOrder) {
			Product product = productService.getOneProduct(order.getProduct().getId());
			Order objOrder = new Order(null, product, order.getQuantity(), order.getAmount(), 0);
			listOrderAdd.add(objOrder);
		}
		newTransaction.setOrder(listOrderAdd);
		System.out.println("Size: " + listOrderAdd.size());
		
		Transaction transactionAdd = transactionService.addTransaction(newTransaction);
		
		
		
		CreateResponse response = new CreateResponse("success", transactionAdd.getId());
		return new ResponseEntity<CreateResponse>(response, HttpStatus.OK);
		
	}
}
