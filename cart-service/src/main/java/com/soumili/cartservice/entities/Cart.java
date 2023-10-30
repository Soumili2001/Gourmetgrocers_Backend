package com.soumili.cartservice.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soumili.cartservice.payloads.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cart_id;
	
	private int totalItems;
	
	private double totalPrices;
	
	private String username;

	@OneToMany(cascade = CascadeType.ALL,
     mappedBy = "cart",orphanRemoval=true)
	@JsonManagedReference
	private Set<CartItem> cartItem;

	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(int cart_id, int totalItems, double totalPrices, String username, Set<CartItem> cartItem) {
		super();
		this.cart_id = cart_id;
		this.totalItems = totalItems;
		this.totalPrices = totalPrices;
		this.username = username;
		this.cartItem = cartItem;
	
	}

	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public double getTotalPrices() {
		return totalPrices;
	}

	public void setTotalPrices(double totalPrices) {
		this.totalPrices = totalPrices;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<CartItem> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Set<CartItem> cartItem) {
		this.cartItem = cartItem;
	}

	

}
