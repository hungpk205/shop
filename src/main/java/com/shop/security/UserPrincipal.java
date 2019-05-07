package com.shop.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.entities.Account;
import com.shop.entities.Role;

public class UserPrincipal implements UserDetails {

	private int id;
	@JsonIgnore
	private String username;
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserPrincipal create(Account account) {
		Set<Role> roles = account.getRole();
		List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return new UserPrincipal(account.getId(), account.getUsername(), account.getPassword(), authorities);
	}

	public int getId() {
		return id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
