package com.soumili.orderservice.services;

import java.util.List;

import com.soumili.orderservice.entities.Order;
import com.soumili.orderservice.payloads.BillingResponse;

public interface OrderServiceInter {

	public void save(String username, BillingResponse billingResponse);

	public List<Order> findOrderByUsername(String username);

	public List<Order> findAllOrders(String username);

	public void cancelOrder(String username, int order_id);
    
	public List<Order> findOrder(String username);

	Order findOrderById(int order_id);
}
