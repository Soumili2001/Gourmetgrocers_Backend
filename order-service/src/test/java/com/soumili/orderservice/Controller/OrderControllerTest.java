package com.soumili.orderservice.Controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumili.orderservice.controller.OrderController;
import com.soumili.orderservice.entities.Order;
import com.soumili.orderservice.payloads.BillingResponse;
import com.soumili.orderservice.services.OrderService;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {
	
	
	

		@MockBean
		OrderService orderService;

		@Autowired
		MockMvc mockMvc;

		@Test
		public void createOrder() throws Exception {
			BillingResponse bill= new BillingResponse("abc","cash");
			
			doNothing().when(orderService).save("soumili",bill);
			mockMvc.perform(post("/orders/{username}", "soumili").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(bill)));

		}
		@Test
		public void cancelOrder() throws Exception {
			doNothing().when(orderService).cancelOrder("soumili",1);
			mockMvc.perform(delete("/orders/{username}/{order_id}", "soumili",1).contentType(MediaType.APPLICATION_JSON));

		}
		@Test
		public void viewAllOrder() throws Exception {
			List<Order> o = List.of(new Order(1,"Soumili",null, 80.0, 8.0, 7,"cash","abc", null));
			when(orderService.findAllOrders("soumili")).thenReturn(o);
			mockMvc.perform(get("/orders/All/{username}", "soumili",1).contentType(MediaType.APPLICATION_JSON));

		}
		@Test
		public void viewOrder() throws Exception {
			List<Order> o = List.of(new Order(1,"Soumili",null, 80.0, 8.0, 7,"cash","abc", null));
			when(orderService.findOrder("soumili")).thenReturn(o);
			mockMvc.perform(get("/orders/{username}", "soumili",1).contentType(MediaType.APPLICATION_JSON));

		}
		@Test
		public void viewOrderByUsername() throws Exception {
			List<Order> o = List.of(new Order(1,"Soumili",null, 80.0, 8.0, 7,"cash","abc", null));
			when(orderService.findOrderByUsername("soumili")).thenReturn(o);
			mockMvc.perform(get("/orders/find/{username}", "soumili",1).contentType(MediaType.APPLICATION_JSON));

		}
		@Test
		public void viewOrderById() throws Exception {
			Order o = new Order(1,"Soumili",null, 80.0, 8.0, 7,"cash","abc", null);
			when(orderService.findOrderById(1)).thenReturn(o);
			mockMvc.perform(get("/orders/Id/{order_id}", "soumili",1).contentType(MediaType.APPLICATION_JSON));

		}

		

}
