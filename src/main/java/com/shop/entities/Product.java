package com.shop.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column
	private String name;
	@NotBlank
	@Column
	private String description;
	@NotBlank
	@Column
	private String detail;
	@NotBlank
	@Column
	private String picture;
	@NotNull
	@Column
	private int quantity;
	@NotNull
	@Column
	private float price;
	@Column
	private int count_buy;
	@Column
	private int active;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Account account;
	
	@Column
	private Timestamp created_at;
	
	@JsonIgnoreProperties("category")
	@ManyToOne
	@JoinColumn(name = "id_category")
	private Category category;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCount_buy() {
		return count_buy;
	}

	public void setCount_buy(int count_buy) {
		this.count_buy = count_buy;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Product(Integer id, String name, String description, String detail, String picture, int quantity, float price, int count_buy, Account account, Timestamp created_at,
			Category category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.picture = picture;
		this.quantity = quantity;
		this.price = price;
		this.count_buy = count_buy;
		this.account = account;
		this.created_at = created_at;
		this.category = category;
	}

	public Product() {
		super();
	}

	
	
	
	
}
