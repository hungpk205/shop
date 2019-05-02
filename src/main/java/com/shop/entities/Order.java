package com.shop.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_transaction")
	private Transaction transaction;
	
	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
	
	@Column
	private int quantity;
	@Column
	private float amount;
	@Column
	private int status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Order(Integer id, Transaction transaction, Product product, int quantity, float amount, int status) {
		super();
		this.id = id;
		this.transaction = transaction;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	public Order(Integer id, Product product, int quantity, float amount, int status) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	public Order() {
		super();
	}
	
	
}
