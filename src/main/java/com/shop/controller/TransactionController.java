package com.shop.controller;

import java.security.Principal;
import java.sql.Timestamp;
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
import com.shop.entities.Cart;
import com.shop.entities.Order;
import com.shop.entities.Payment;
import com.shop.entities.Product;
import com.shop.entities.Role;
import com.shop.entities.Transaction;
import com.shop.request.TransactionRequest;
import com.shop.response.CreateResponse;
import com.shop.service.AccountService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.PaymentService;
import com.shop.service.ProductService;
import com.shop.service.TransactionService;
import com.shop.utils.MessengerUtils;

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
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("alll")
	public ResponseEntity<List<Transaction>> getAlll(){
		List<Transaction> list =  transactionService.getAll();
		return new ResponseEntity<List<Transaction>>(list,HttpStatus.OK);
		
	}
	
	
	//Get all
	@GetMapping("all")
	public ResponseEntity<?> getAll(Principal user){
		//Only admin
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isAdmin = false;
		for (Role role : roles) {
			if (role.getName().equals("ADMIN")) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			MessengerUtils response = new MessengerUtils(false, "Not have role");
			return new ResponseEntity<MessengerUtils>(response, HttpStatus.FORBIDDEN);
		}
		
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
					SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
					
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
	
	
	//Get transaction of customer
	@GetMapping("customer")
	public ResponseEntity<?> getTransactionOfCustomer(Principal user){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		Set<Role> roles = accountLogin.getRole();
		boolean isCustomer = false;
		for (Role role : roles) {
			if (role.getName().equals("CUSTOMER")) {
				isCustomer = true;
				break;
			}
		}
		if (!isCustomer) {
			MessengerUtils response = new MessengerUtils(false, "Not have role");
			return new ResponseEntity<MessengerUtils>(response, HttpStatus.FORBIDDEN);
		}
		
		List<Transaction> list =  transactionService.getTransactionOfAccount(accountLogin.getId());
		
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
					SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
					
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
	
	//Get a transaction of customer by id
	@GetMapping("customer/{idTransaction}")
	public ResponseEntity<?> getTransactionById(Principal user, @PathVariable("idTransaction") int idTransaction){
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		Transaction transaction =  transactionService.getTransactionById(accountLogin.getId(), idTransaction);
		

		BuyerDTO buyer = new BuyerDTO();
		
		if (transaction.getAccount()!= null) {
			buyer = new BuyerDTO(transaction.getAccount().getProfile().getFullname(), transaction.getAccount().getProfile().getPhone(), transaction.getAccount().getProfile().getAddress());
		} else {
			buyer = new BuyerDTO(transaction.getUser_name(), transaction.getUser_phone(), transaction.getAddress());
		}
		
		Set<Order> listOrder = transaction.getOrder();
		List<OrderInformation> listOrderInfor = new ArrayList<>();
		
		for (Order order : listOrder) {
			SellerDTO seller = new SellerDTO(order.getProduct().getAccount().getProfile().getFullname(), order.getProduct().getAccount().getProfile().getPhone(), order.getProduct().getAccount().getProfile().getEmail(), order.getProduct().getAccount().getProfile().getAddress());
			
			OrderInformation orderInfor = new OrderInformation(order.getId(), seller ,order.getProduct().getName(), order.getQuantity(), order.getAmount(), order.getStatus());
			
			listOrderInfor.add(orderInfor);
		}
		
		String created_at = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCreated_at());
		
		TransactionDTO transactionDTO = new TransactionDTO(transaction.getId(), buyer, listOrderInfor, transaction.getAmount(), transaction.getPayment().getName(), transaction.getPayment_infor(), transaction.getMessage(), created_at, transaction.getStatus());
		
		return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.OK);
		
	}
	
	//Add transaction
	@PostMapping("add")
	public ResponseEntity<CreateResponse> add(Principal user, @RequestBody TransactionRequest transactionRequest){
		//Get infor from cart
		Account accountLogin = accountService.getAccountByUsername(user.getName());
		
		Transaction newTransaction = new Transaction();
		
		List<Cart> listCart = cartService.getCartOfAccount(accountLogin.getId());
		
		float amount = 0;
		for (Cart cart : listCart) {
			amount += cart.getAmount();
		}
		newTransaction.setAmount(amount);
		newTransaction.setAccount(accountLogin);
		newTransaction.setUser_name(transactionRequest.getFullname());
		newTransaction.setUser_phone(transactionRequest.getPhone());
		newTransaction.setAddress(transactionRequest.getAddress());
		
		Payment payment = paymentService.getPaymentById(transactionRequest.getId_payment());
		newTransaction.setPayment(payment);
		
		//List product
		Set<Order> listOrderAdd = new HashSet<>();
		
		//Add cart to order
		for (Cart cart: listCart) {
			Product product = cart.getProduct();
			int quantityCart = cart.getQuantity();
			float amountCart = cart.getAmount();
			Order order = new Order(null, product, quantityCart, amountCart, 0);
			
			listOrderAdd.add(order);
		}
		newTransaction.setOrder(listOrderAdd);
		
		System.out.println("Size: " + listOrderAdd.size());
		
		Timestamp created_at = new Timestamp(System.currentTimeMillis());
		newTransaction.setCreated_at(created_at);
		
		Transaction transactionAdd = transactionService.addTransaction(newTransaction);
		
		//Clear cart
		cartService.DeleteCartOfAccount(accountLogin.getId());
		
		CreateResponse response = new CreateResponse("success", transactionAdd.getId());
		return new ResponseEntity<CreateResponse>(response, HttpStatus.OK);
		
	}
}
