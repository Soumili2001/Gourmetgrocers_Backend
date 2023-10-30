package com.soumili.userservice.payloads;

import java.time.LocalDate;
import java.util.List;




public class Order {

	
	private int order_id;
	private String username;
	private LocalDate orderDate;
	private double totalPrice;
	private double tax;
	private int quantity;
	private String paymentMethod;
	private String address;
	

	
	private List<OrderDetail> orderDetailList;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(int order_id, String username, LocalDate orderDate, double totalPrice, double tax, int quantity,
			String paymentMethod, String address, List<OrderDetail> orderDetailList) {
		super();
		this.order_id = order_id;
		this.username = username;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.tax = tax;
		this.quantity = quantity;
		this.paymentMethod = paymentMethod;
		this.address = address;
		this.orderDetailList = orderDetailList;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", username=" + username + ", orderDate=" + orderDate + ", totalPrice="
				+ totalPrice + ", tax=" + tax + ", quantity=" + quantity + ", paymentMethod=" + paymentMethod
				+ ", address=" + address + ", orderDetailList=" + orderDetailList + "]";
	}

	
}
