package com.shop.request;

public class TransactionRequest {
	private int id_payment;
	private String fullname;
	private String phone;
	private String address;
	
	public int getId_payment() {
		return id_payment;
	}
	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public TransactionRequest(int id_payment, String fullname, String phone, String address) {
		super();
		this.id_payment = id_payment;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
	}
	public TransactionRequest() {
		super();
	}
	
}
