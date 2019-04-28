package com.shop.dto;

import java.util.List;
import java.util.Set;

import com.shop.entities.Permission;
import com.shop.entities.Profile;

public class AccountDTO {
	private int id;
	private String username;
	private int status;
	private Profile profile;
	private String role;
	private List<String> permission;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public List<String> getPermission() {
		return permission;
	}
	public void setPermission(List<String> permission) {
		this.permission = permission;
	}
	public AccountDTO(int id, String username, int status, Profile profile) {
		super();
		this.id = id;
		this.username = username;
		this.status = status;
		this.profile = profile;
	}
	public AccountDTO() {
		super();
	}
	
}
