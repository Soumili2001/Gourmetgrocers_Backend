package com.soumili.cartservice.Controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumili.cartservice.entities.Cart;
import com.soumili.cartservice.entities.CartItem;
import com.soumili.cartservice.payloads.ItemResponse;
import com.soumili.cartservice.services.CartService;

@WebMvcTest(CartController.class)
public class CartServiceController {

	@MockBean
	CartService cartService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testaddItem() throws Exception {
		ItemResponse item = new ItemResponse(1, 5);
		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		when(cartService.addItemToCart(item, "soumili")).thenReturn(c);
		mockMvc.perform(post("/cart/{username}", "soumili").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(c)));

	}

	@Test
	public void testdecreaseIteminCart() throws Exception {
		ItemResponse item = new ItemResponse(1, 5);
		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		when(cartService.decreaseItemInCart(item, "soumili")).thenReturn(c);
		mockMvc.perform(put("/cart/{username}", "soumili").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(c)));

	}

	@Test
	public void testdeleteIteminCart() throws Exception {

		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		doNothing().when(cartService).deleteItemFromCart(1, "soumili");
		mockMvc.perform(delete("/cart/{username}/{product_id}", "soumili", 1).contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void testdeleteCartById() throws Exception {

		doNothing().when(cartService).deleteCartById(1);
		mockMvc.perform(delete("/cart/{cart_id}", "soumili", 1).contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void virewCart() throws Exception {

		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		when(cartService.getCartById("soumili", 1)).thenReturn(c);
		mockMvc.perform(get("/cart/Id/{cart_id}", 1).contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void virewCartByUsername() throws Exception {

		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		when(cartService.getCartByUsername("soumili")).thenReturn(c);
		mockMvc.perform(get("/cart/find/{username}", "soumili").contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	public void FindCart() throws Exception {

		Set<CartItem> cartItem = new HashSet<CartItem>();
		Cart c = new Cart(1, 50, 100, "soumili", cartItem);
		when(cartService.getCart("soumili")).thenReturn(c);
		mockMvc.perform(get("/cart/{username}", "soumili").contentType(MediaType.APPLICATION_JSON));

	}

}
