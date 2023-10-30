package com.soumili.orderservice.payloads;

import java.util.List;

import com.soumili.orderservice.entities.Order;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class User {

	private int userId;

	private String username;

	private String email;

	private String fullName;

	private String userRole;

	private boolean isLoggedIn;

	private List<Order> order;

	private Cart cart;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String username, String email, String fullName, String userRole, boolean isLoggedIn,
			List<Order> order, Cart cart) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.userRole = userRole;
		this.isLoggedIn = isLoggedIn;
		this.order = order;
		this.cart = cart;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", email=" + email + ", fullName=" + fullName
				+ ", userRole=" + userRole + ", isLoggedIn=" + isLoggedIn + ", order=" + order + "]";
	}

}
