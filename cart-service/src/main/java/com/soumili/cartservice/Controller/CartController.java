package com.soumili.cartservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soumili.cartservice.entities.Cart;
import com.soumili.cartservice.payloads.ApiResponse;
import com.soumili.cartservice.payloads.ItemResponse;
import com.soumili.cartservice.services.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	
	@PostMapping("/{username}")
	public Cart addItemToCart(@Valid @RequestBody ItemResponse itemResponse,
			@PathVariable String username) {
		Cart c=cartService.addItemToCart(itemResponse, username);
		return c;
	}

	@PutMapping("/{username}")
	public  Cart updateItemInCart(@Valid @RequestBody ItemResponse itemResponse,
			@PathVariable String username) {
		Cart c=cartService.decreaseItemInCart(itemResponse, username);
		return c;
	}

	@DeleteMapping("/{username}/{productId}")
	public  ApiResponse DeleteItemFromCart(@PathVariable int productId, @PathVariable String username) {
		cartService.deleteItemFromCart(productId, username);
		return new ApiResponse("Product is successfully removed from cart!");
	}

	@GetMapping("/find/{username}")
	public Cart viewCartByUsername(@PathVariable("username") String username) {
		Cart result = cartService.getCartByUsername(username);
		return result;
	}

	@GetMapping("/{username}")
	public Cart viewCart(@PathVariable("username") String username) {
		Cart result = cartService.getCart(username);
		return result;
	}

	@GetMapping("/Id/{cart_id}")
	public Cart viewCartByUsername( @RequestParam String username, @PathVariable int cart_id) {
		Cart result = cartService.getCartById(username, cart_id);
		return result;
	}

	@DeleteMapping("/delete/{cart_id}")
	public void DeleteCartById(@PathVariable("cart_id") int cart_id) {
		cartService.deleteCartById(cart_id);

	}
}