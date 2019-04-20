package com.shop.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private String detail;
	@Column
	private String picture;
	@Column
	private String img1;
	@Column
	private String img2;
	@Column
	private String img3;
	@Column
	private int quantity;
	@Column
	private float price;
	@Column
	private int count_buy;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Account account;
	@Column
	private Date created_at;
	
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

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
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

	public Product(Integer id, String name, String description, String detail, String picture, String img1, String img2,
			String img3, int quantity, float price, int count_buy, Account account, Date created_at,
			Category category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.detail = detail;
		this.picture = picture;
		this.img1 = img1;
		this.img2 = img2;
		this.img3 = img3;
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
