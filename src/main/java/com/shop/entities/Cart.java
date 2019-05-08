package com.shop.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_account")
	private Account account;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_product")
	private Product product;
	
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "amount")
	private float amount;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Cart(Integer id, Account account, Product product, int quantity, float amount) {
		super();
		this.id = id;
		this.account = account;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
	}
	public Cart() {
		super();
	}
	
	
}
