package com.soumili.cartservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soumili.cartservice.payloads.ProductDto;

@Entity
@Table(name = "cart_item")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartItem_id;
	private int product_id;
	private int quantity;
    private double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {
    		CascadeType.MERGE
            
        })
    @JoinColumn(name="cart_id",referencedColumnName = "cart_id")
    @JsonBackReference
    private Cart cart;
    
    @Transient
    private ProductDto product;

	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(int cartItem_id, int product_id, int quantity, double totalPrice, Cart cart, ProductDto product) {
		super();
		this.cartItem_id = cartItem_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.cart = cart;
		this.product = product;
	}

	public int getCartItem_id() {
		return cartItem_id;
	}

	public void setCartItem_id(int cartItem_id) {
		this.cartItem_id = cartItem_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public ProductDto getProdutDto() {
		return product;
	}

	public void setProdutDto(ProductDto product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "CartItem [cartItem_id=" + cartItem_id + ", product_id=" + product_id + ", quantity=" + quantity
				+ ", totalPrice=" + totalPrice + ", cart=" + cart + ", product=" + product + "]";
	}

	
}
