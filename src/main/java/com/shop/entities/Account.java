package com.shop.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_profile")
	private Profile profile;
	
	@Column
	private int status;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "account_role",
			joinColumns = @JoinColumn(name = "id_account"),
			inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<Role> role = new HashSet<>();
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "account_permission",
			joinColumns = @JoinColumn(name = "id_account"),
			inverseJoinColumns = @JoinColumn(name = "id_permission"))
	private Set<Permission> permission = new HashSet<>();
	
	@OneToMany(mappedBy = "account")
	private Set<Product> listProduct = new HashSet<>();
	
	@OneToMany(mappedBy = "account")
	private Set<Cart> listCart = new HashSet<>();
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	//Role
	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	//Permission
	public Set<Permission> getPermission() {
		return permission;
	}

	public void setPermission(Set<Permission> permission) {
		this.permission = permission;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Account(Integer id, String username, String password, int status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
	}
	

	public Account(Integer id, String username, Profile profile, int status, Set<Role> role,
			Set<Permission> permission) {
		super();
		this.id = id;
		this.username = username;
		this.profile = profile;
		this.status = status;
		this.role = role;
		this.permission = permission;
	}

	public Account(Integer id, String username, String password, Profile profile, int status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.profile = profile;
		this.status = status;
	}

	public Account(Account objAccount) {
		this.username = objAccount.getUsername();
		this.password = objAccount.getPassword();
		this.profile = objAccount.getProfile();
		this.status = objAccount.getStatus();
	}
	
	public Account() {
		super();
	}
	
	
}
