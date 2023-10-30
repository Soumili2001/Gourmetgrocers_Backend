package com.soumili.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumili.orderservice.entities.Order;
import com.soumili.orderservice.payloads.ApiResponse;
import com.soumili.orderservice.payloads.BillingResponse;
import com.soumili.orderservice.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/{username}")
	public ApiResponse createOrder(@Valid @RequestBody BillingResponse billingResponse,
			@PathVariable("username") String username) {

		orderService.save(username, billingResponse);
		return new ApiResponse("Order is successfully placed");
	}

	@GetMapping("/find/{username}")
	public List<Order> getOrdersByUsername(@PathVariable("username") String username) {
		List<Order> orderList = orderService.findOrderByUsername(username);
		return orderList;
	}

	@GetMapping("/{username}")
	public List<Order> getOrders(@PathVariable("username") String username) {
		List<Order> orderList = orderService.findOrder(username);
		return orderList;
	}

	@GetMapping("/All/{username}")
	public List<Order> getAllOrders(@PathVariable("username") String username) {
		List<Order> allOrders = orderService.findAllOrders(username);

		return allOrders;
	}
	@GetMapping("/Id/{order_id}")
    public Order getOrdersById(@PathVariable("order_id") int order_id) {
     Order o = orderService.findOrderById(order_id);

	return o;

	}

	@DeleteMapping("/{username}/{order_id}")
	public ApiResponse cancelOrder(@PathVariable("username") String username, @PathVariable int order_id) {

		orderService.cancelOrder(username, order_id);
		return new ApiResponse("Order is successfully cancelled");

	}

}
