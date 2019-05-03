package com.shop.dto;

import java.util.List;

public class TransactionDTO {
	private int id;
	private BuyerDTO buyer;
	private List<OrderInformation> order;
	private float amount;
	private String payment;
	private String payment_infor;
	private String message;
	private String created_at;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BuyerDTO getBuyer() {
		return buyer;
	}
	public void setBuyer(BuyerDTO buyer) {
		this.buyer = buyer;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPayment_infor() {
		return payment_infor;
	}
	public void setPayment_infor(String payment_infor) {
		this.payment_infor = payment_infor;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public TransactionDTO() {
		super();
	}

	public List<OrderInformation> getOrder() {
		return order;
	}
	public void setOrder(List<OrderInformation> order) {
		this.order = order;
	}
	
	public TransactionDTO(int id, BuyerDTO buyer, List<OrderInformation> order, float amount,
			String payment, String payment_infor, String message, String created_at, int status) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.order = order;
		this.amount = amount;
		this.payment = payment;
		this.payment_infor = payment_infor;
		this.message = message;
		this.created_at = created_at;
		this.status = status;
	}
	
}
