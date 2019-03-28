package com.example.demo.domain;
import java.io.Serializable;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Document
public class SysUser implements UserDetails, Serializable { //1

	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String username;
	private String password;
	private String description;
	private String phone;
	private String email;
	@Field
	private Set<Orders> orders=new HashSet<>();
	private List<SysRole> roles;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //2
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		List<SysRole> roles = this.getRoles();
		for (SysRole role : roles) {
			auths.add(new SimpleGrantedAuthority(role.getName()));
		}
		return auths;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public SysUser(String username, String password, String description, String phone, String email, Set<Orders> orders, List<SysRole> roles) {
		this.username = username;
		this.password = password;
		this.description = description;
		this.phone = phone;
		this.email = email;
		this.orders = orders;
		this.roles = roles;
	}

	public SysUser() {
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}
