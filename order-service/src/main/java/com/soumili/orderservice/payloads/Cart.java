package com.soumili.orderservice.payloads;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;


//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.soumili.userservice.entities.User;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
import jakarta.persistence.Transient;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;


public class Cart {


private int cart_id;
private int totalItems;
private double totalPrices;
private Set<CartItem> cartItem;
private String username;


public Cart() {
	super();
	// TODO Auto-generated constructor stub
}

public Cart(int cart_id, int totalItems, double totalPrices, Set<CartItem> cartItem,String username) {
	super();
	this.cart_id = cart_id;
	this.totalItems = totalItems;
	this.totalPrices = totalPrices;
	this.cartItem = cartItem;
	this.username=username;
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

public Set<CartItem> getCartItem() {
	return cartItem;
}

public void setCartItem(Set<CartItem> cartItem) {
	this.cartItem = cartItem;
}


@Override
public String toString() {
	return "Cart [cart_id=" + cart_id + ", totalItems=" + totalItems + ", totalPrices=" + totalPrices + ", cartItem="
			+ cartItem + "]";
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}


}


