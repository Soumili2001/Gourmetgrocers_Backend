package com.soumili.orderservice.services;

import java.time.Period;
import java.util.*;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soumili.orderservice.Exception.ResourceNotFoundException;
import com.soumili.orderservice.Exception.UserNotAllowedException;
import com.soumili.orderservice.Exception.UserNotLoggedInException;
import com.soumili.orderservice.entities.Order;
import com.soumili.orderservice.entities.OrderDetail;
import com.soumili.orderservice.payloads.BillingResponse;
import com.soumili.orderservice.payloads.Cart;
import com.soumili.orderservice.payloads.CartItem;
import com.soumili.orderservice.payloads.ProductDto;
import com.soumili.orderservice.payloads.User;
import com.soumili.orderservice.repository.OrderDetailRepository;
import com.soumili.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderServiceInter {

	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private final OrderRepository orderRepo;

	@Autowired
	private final OrderDetailRepository orderDetailRepo;

	Logger logger=LoggerFactory.getLogger(OrderService.class);
	@Override
	public void save(String username, BillingResponse billingResponse) {
		User user = getUser(username);
		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		String address = billingResponse.getAddress();
		String paymentMethod = billingResponse.getPaymentMethod();
		Cart cart = getCart(username);
		if (cart != null && cart.getCartItem().size() >= 1) {
			Order order = new Order();
			order.setOrderDate(LocalDate.now());
			order.setUsername(user.getUsername());
			order.setTax(taxCalculate(cart.getTotalPrices()));
			order.setTotalPrice(cart.getTotalPrices() + order.getTax());
			order.setAddress(address);
			order.setPaymentMethod(paymentMethod);
			order.setQuantity(cart.getTotalItems());
			List<OrderDetail> orderDetailList = new ArrayList<>();
			for (CartItem item : cart.getCartItem()) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProductDto(item.getProdutDto());
				orderDetail.setProduct_id(item.getProduct_id());
				orderDetail.setProduct_quantity(item.getQuantity());
				orderDetailRepo.save(orderDetail);
				orderDetailList.add(orderDetail);

				int i = 0;
				while (i < item.getQuantity()) {
					restTemplate.put("http://PRODUCT-SERVICE/products/decrease/" + item.getProduct_id(),
							ProductDto.class);
					i++;
				}

			}
			order.setOrderDetailList(orderDetailList);
			restTemplate.delete("http://CART-SERVICE/cart/delete/" + cart.getCart_id(), Cart.class);

			orderRepo.save(order);
		} else {
			throw new ResourceNotFoundException("No items added to cart!!!");
		}
		logger.info("Order is created successfully");
	}

	@Override
	public List<Order> findOrderByUsername(String username) {
		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		List<Order> orders = orderRepo.findAllByUsername(username);
		if (orders == null) {
			throw new ResourceNotFoundException("Order is not yet placed!!");
		}

		for (Order o : orders) {
			List<OrderDetail> items = o.getOrderDetailList();

			if (items != null) {
				for (OrderDetail i : items) {
					ProductDto product = getProduct(i.getProduct_id());
					i.setProductDto(product);

				}

			}
		}
		logger.info("All the orders are successfully fetched");
		return orders;

	}

	@Override
	public List<Order> findAllOrders(String username) {
		User user = getUser(username);

		if (!(user.getUserRole().equalsIgnoreCase("Admin"))) {
			throw new UserNotAllowedException();
		}
		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		List<Order> allOrders = orderRepo.findAll();
		for (Order o : allOrders) {
			if (o != null) {
				List<OrderDetail> items = o.getOrderDetailList();

				for (OrderDetail i : items) {
					ProductDto product = getProduct(i.getProduct_id());
					i.setProductDto(product);

				}

			}
		}

		return allOrders;

	}

	@Override
	public Order findOrderById(int order_id) {
	Order o = orderRepo.findById(order_id).get();
    if (o == null) {
    	throw new ResourceNotFoundException("Order is not yet placed!!");

	}
	List<OrderDetail> items = o.getOrderDetailList();
    for (OrderDetail i : items) {
    ProductDto product = getProduct(i.getProduct_id());
    i.setProductDto(product);
	}

	return o;
}

	
	@Override
	public void cancelOrder(String username, int order_id) {
		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		Order o = orderRepo.findById(order_id)
				.orElseThrow(() -> new ResourceNotFoundException("Order is not yet placed!!"));

		Period diff = Period.between(o.getOrderDate(), LocalDate.now());
		long m = diff.getMonths();
		long d = diff.getDays();
		long y = diff.getYears();
		long total = (y * 365) + (m * 30) + d;
		if (total < 2) {
			for (OrderDetail item : o.getOrderDetailList()) {

				int i = 0;
				while (i < item.getProduct_quantity()) {
					restTemplate.put("http://PRODUCT-SERVICE/products/update/" + item.getProduct_id(),
							ProductDto.class);
					i++;
				}

			}
			orderRepo.deleteById(order_id);
		} else {
			throw new ResourceNotFoundException("More than 2 days passed order cannot be cancelled");
		}
	}

	public User getUser(String username) {
		try {
			User user = restTemplate.getForObject("http://USER-SERVICE/user/" + username, User.class);
			return user;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This user not registered yet!!.");
		}
	}

	public ProductDto getProduct(int product_id) {
		try {
			ProductDto p = restTemplate.getForObject("http://PRODUCT-SERVICE/products/Id/" + product_id,
					ProductDto.class);
			return p;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This Product does not exists.");
		}
	}

	public Cart getCart(String username) {
		try {
			Cart cart = restTemplate.getForObject("http://CART-SERVICE/cart/" + username, Cart.class);

			return cart;
		} catch (Exception e) {
			throw new ResourceNotFoundException("No items added to cart!!!");
		}
	}

	@Override
	public List<Order> findOrder(String username) {

		List<Order> orders = orderRepo.findAllByUsername(username);
		if (orders == null) {
			throw new ResourceNotFoundException("Order is not yet placed!!");
		}

		for (Order o : orders) {
			List<OrderDetail> items = o.getOrderDetailList();

			for (OrderDetail i : items) {
				ProductDto product = getProduct(i.getProduct_id());
				i.setProductDto(product);

			}

		}
		return orders;

	}

	public double taxCalculate(double totalPrice) {
		double tax;
		if (totalPrice <= 1000) {
			tax = totalPrice * 0.02;
		} else if (totalPrice <= 5000) {
			tax = 1000 * 0.02 + (totalPrice - 1000) * 0.05;
		} else {
			tax = (1000 * 0.02) + (4000 * 0.05) + (totalPrice - 5000) * 0.125;
		}
		return tax;
	}
}
