package com.soumili.userservice.entities;

import java.util.List;

import com.soumili.userservice.payloads.Cart;
import com.soumili.userservice.payloads.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Users")
@SuperBuilder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@NotBlank(message = "This field cannot be empty")
	@Size(min = 2, max = 30, message = "The username field must be between 2 to 30 characters")
	private String username;

	@NotBlank(message = "This field cannot be empty")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "The password must have Min 8 characters, at least one letter, one number and one special character")
	private String password;

	@NotBlank(message = "This field cannot be empty")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Invalid Email Id")
	private String email;
	@NotBlank(message = "This field cannot be empty")
	private String fullName;

	@Builder.Default
	private String userRole = "user";
	@Builder.Default
	private boolean isLoggedIn = false;

	@Transient
	private List<Order> order;

	@Transient
	private Cart cart;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId,
			@NotBlank(message = "This field cannot be empty") @Size(min = 2, max = 30, message = "The name field must be between 2 to 30 characters") String username,
			@NotBlank(message = "This field cannot be empty") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "The password must have Min 8 characters, at least one letter, one number and one special character") String password,
			@NotBlank(message = "This field cannot be empty") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email Id") String email,
			@NotBlank(message = "This field cannot be empty") String fullName, String userRole, boolean isLoggedIn,
			List<Order> order, Cart cart) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", fullName=" + fullName + ", userRole=" + userRole + ", isLoggedIn=" + isLoggedIn + ", orderDto="
				+ order + "]";
	}

}
