package com.soumili.userservice.payloads;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soumili.userservice.payloads.ProductDto;

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

public class OrderDetail {

	private int orderItem_id;

	private Order order;
	private int product_id;
	private int product_quantity;
	@Transient
	private ProductDto product;

	public OrderDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetail(int orderItem_id, Order order, int product_id, int product_quantity, ProductDto product) {
		super();
		this.orderItem_id = orderItem_id;
		this.order = order;
		this.product_id = product_id;
		this.product_quantity = product_quantity;
		this.product = product;
	}

	public int getOrderItem_id() {
		return orderItem_id;
	}

	public void setOrderItem_id(int orderItem_id) {
		this.orderItem_id = orderItem_id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public ProductDto getProductDto() {
		return product;
	}

	public void setProductDto(ProductDto product) {
		this.product = product;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	@Override
	public String toString() {
		return "OrderDetail [orderItem_id=" + orderItem_id + ", order=" + order + ", product_id=" + product_id
				+ ", product_quantity=" + product_quantity + ", product=" + product + "]";
	}

}
