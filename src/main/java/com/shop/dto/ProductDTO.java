package com.shop.dto;

import java.sql.Timestamp;
import java.util.List;

public class ProductDTO {
	
	private String message;
	private int id;
	private String name;
	private String category;
	private String description;
	private String detail;
	private List<String> image;
	private int quantity;
	private float price;
	private int count_buy;
	private String created_by;
	private String created_at;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
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
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public ProductDTO(String message,int id, String name, String category, String description, String detail, List<String> image,
			int quantity, float price, int count_buy, String created_by, String created_at) {
		super();
		this.id = id;
		this.message = message;
		this.name = name;
		this.category = category;
		this.description = description;
		this.detail = detail;
		this.image = image;
		this.quantity = quantity;
		this.price = price;
		this.count_buy = count_buy;
		this.created_by = created_by;
		this.created_at = created_at;
	}
	public ProductDTO() {
		super();
	}
	
}
