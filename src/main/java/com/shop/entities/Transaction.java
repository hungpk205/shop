package com.shop.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_account")
	private Account account;
	
	@Column
	private String user_name;
	@Column
	private String user_phone;
	@Column
	private String user_email;
	@Column
	private String address;
	@Column
	private float amount;
	
	@ManyToOne
	@JoinColumn( name = "id_payment")
	private Payment payment;
	
	@Column
	private String payment_infor;
	@Column
	private String message;
	@Column
	private Timestamp created_at;
	@Column
	private int status;
	
	@OneToMany(mappedBy = "transaction")
	private Set<Order> order = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
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

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	public Transaction(Integer id, Account account, String user_name, String user_phone, String user_email,
			String address, float amount, Payment payment, String payment_infor, String message, Timestamp created_at,
			int status, Set<Order> order) {
		super();
		this.id = id;
		this.account = account;
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.user_email = user_email;
		this.address = address;
		this.amount = amount;
		this.payment = payment;
		this.payment_infor = payment_infor;
		this.message = message;
		this.created_at = created_at;
		this.status = status;
		this.order = order;
	}

	public Transaction() {
		super();
	}
	
	
	
}
