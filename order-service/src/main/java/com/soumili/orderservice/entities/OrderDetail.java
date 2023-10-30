package com.soumili.orderservice.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soumili.orderservice.payloads.ProductDto;

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

@Entity
@Table(name="order_detail")
public class OrderDetail {
	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int orderItem_id;
@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
@JoinColumn(name="order_id", referencedColumnName="order_id")
@JsonBackReference
private Order order;
private int product_id;
private int product_quantity;
@Transient
private ProductDto productDto;
public OrderDetail() {
	super();
	// TODO Auto-generated constructor stub
}

public OrderDetail(int orderItem_id, Order order, int product_id, int product_quantity, ProductDto productDto) {
	super();
	this.orderItem_id = orderItem_id;
	this.order = order;
	this.product_id = product_id;
	this.product_quantity = product_quantity;
	this.productDto = productDto;
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
	return productDto;
}
public void setProductDto(ProductDto productDto) {
	this.productDto = productDto;
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
			+ ", product_quantity=" + product_quantity + ", productDto=" + productDto + "]";
}


}
