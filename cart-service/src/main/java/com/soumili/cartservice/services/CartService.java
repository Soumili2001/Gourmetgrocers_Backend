package com.soumili.cartservice.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soumili.cartservice.Exception.ProductOutOfStockException;
import com.soumili.cartservice.Exception.ResourceNotFoundException;
import com.soumili.cartservice.Exception.UserNotLoggedInException;
import com.soumili.cartservice.entities.Cart;
import com.soumili.cartservice.entities.CartItem;
import com.soumili.cartservice.payloads.ItemResponse;
import com.soumili.cartservice.payloads.ProductDto;
import com.soumili.cartservice.payloads.User;
import com.soumili.cartservice.repository.CartItemRepository;
import com.soumili.cartservice.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class CartService implements CartServiceInter {

	@Autowired
	private final CartRepository cartRepo ;

	@Autowired
	private final CartItemRepository cartItemRepo;

	@Autowired
	private final RestTemplate restTemplate;


	@Override
	@Transactional
	public Cart addItemToCart(ItemResponse itemResponse, String username) {
		int productId = itemResponse.getProduct_id();
		int quantity = itemResponse.getProduct_quantity();
		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}

		ProductDto product = getProduct(productId);
		if (product.getStock_count() < quantity) {
			throw new ProductOutOfStockException();
		}

		Cart cart = user.getCart();
		if (cart == null) {
			cart = new Cart();
		}
		Set<CartItem> cartItems = cart.getCartItem();
		CartItem cartItem = findCartItem(cartItems, productId);
		if (cartItems == null) {
			cartItems = new HashSet<>();
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProdutDto(product);
				cartItem.setTotalPrice(quantity * product.getProduct_price());
				cartItem.setQuantity(quantity);
				cartItem.setProduct_id(productId);
				cartItem.setCart(cart);
				cartItems.add(cartItem);
				cartItemRepo.save(cartItem);
			}

		} else {
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProdutDto(product);
				cartItem.setTotalPrice(quantity * product.getProduct_price());
				cartItem.setQuantity(quantity);
				cartItem.setProduct_id(productId);
				cartItem.setCart(cart);
				cartItems.add(cartItem);
				cartItemRepo.save(cartItem);
			} else {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getProduct_price()));
				cartItem.setProdutDto(product);
				cartItemRepo.save(cartItem);
			}

		}
		cart.setCartItem(cartItems);
		int totalItems = totalItems(cart.getCartItem());
		double totalPrice = totalPrice(cart.getCartItem());
		cart.setTotalItems(totalItems);
		cart.setTotalPrices(totalPrice);
		cart.setUsername(username);
		Cart c=cartRepo.save(cart);
		return c;

	}

	public Cart decreaseItemInCart(ItemResponse itemResponse, String username) {
		int productId = itemResponse.getProduct_id();
		int quantity = itemResponse.getProduct_quantity();
		User user = getUser(username);
		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}

		ProductDto product = getProduct(productId);
		if (product.getStock_count() < quantity) {
			throw new ProductOutOfStockException();
		}
		Cart cart = user.getCart();
		if (cart != null) {
			Set<CartItem> cartItems = cart.getCartItem();
			CartItem item = findCartItem(cartItems, productId);
			if (item != null) {
				if (quantity >= item.getQuantity()) {
					cartItems.remove(item);
					cartItemRepo.delete(item);
				} else {
					item.setQuantity(item.getQuantity() - quantity);
					item.setTotalPrice(item.getTotalPrice() - (quantity * product.getProduct_price()));
					cartItemRepo.save(item);
				}
				int totalItems = totalItems(cartItems);
				double totalPrice = totalPrice(cartItems);
				cart.setTotalItems(totalItems);
				cart.setTotalPrices(totalPrice);
				cart.setUsername(username);

				Cart c=cartRepo.save(cart);
				return c;
			} else {
				throw new ResourceNotFoundException("item  not yet added to cart!!");
			}
		} else {
			throw new ResourceNotFoundException("No item yet added to cart!!");
		}
	}

	@Override
	public void deleteItemFromCart(int productId, String username) {

		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		Cart cart = user.getCart();
		if (cart != null) {
			Set<CartItem> cartItems = cart.getCartItem();
			    ProductDto product = getProduct(productId);
				CartItem item = findCartItem(cartItems, productId);
				if(item == null) {
					throw new ResourceNotFoundException("item  not yet added to cart!!");
				}
				cartItems.remove(item);
				cartItemRepo.delete(item);
				int totalItems = totalItems(cartItems);
				double totalPrice = totalPrice(cartItems);
				cart.setTotalItems(totalItems);
				cart.setTotalPrices(totalPrice);
				cart.setUsername(username);
				cartRepo.save(cart);
			
		} else {
			throw new ResourceNotFoundException("No item yet added to cart!!");
		}
	}

	@Override
	public void deleteCartById(int cart_id) {
		Cart cart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("This card Id is incorrect!!"));
		for (CartItem cartItem : cart.getCartItem()) {
			cartItemRepo.deleteById(cartItem.getCartItem_id());
		}
		cart.getCartItem().clear();
		cart.setTotalPrices(0);
		
		cart.setTotalItems(0);
		cartRepo.save(cart);
	}

	@Override
	public Cart getCartByUsername(String username) {
		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		Cart cart = cartRepo.findByUsername(username);

		if (cart != null) {
			Set<CartItem> items = cart.getCartItem();
			for (CartItem i : items) {
				ProductDto product = getProduct(i.getProduct_id());
				i.setProdutDto(product);

			}
		}

		return cart;
	}

	@Override
	public Cart getCartById(String username, int cart_id) {
		User user = getUser(username);

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		}
		Cart cart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("This card Id is incorrect!!"));
		if (cart != null) {
			Set<CartItem> items = cart.getCartItem();
			for (CartItem i : items) {
				ProductDto product = getProduct(i.getProduct_id());
				i.setProdutDto(product);
			}
		}
		return cart;
	}

	public  User getUser(String username) {
		try {
			User user = restTemplate.getForObject("http://USER-SERVICE/user/" + username, User.class);
			return user;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This user is not registered yet.");
		}
	}

	public  ProductDto getProduct(int product_id) {
		try {
			ProductDto p = restTemplate.getForObject("http://PRODUCT-SERVICE/products/Id/" + product_id,
					ProductDto.class);
			return p;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This Product does not exists.");
		}
	}

	public  Cart getCart(String username) {

		Cart cart = cartRepo.findByUsername(username);

		if (cart != null) {
			Set<CartItem> items = cart.getCartItem();
			for (CartItem i : items) {
				ProductDto product = getProduct(i.getProduct_id());
				i.setProdutDto(product);

			}
		}

		return cart;
	}

	private  CartItem findCartItem(Set<CartItem> cartItems, int product_id) {
		if (cartItems == null) {
			return null;
		}
		CartItem cartItem = null;
		for (CartItem item : cartItems) {
			if (item.getProduct_id() == product_id) {
				cartItem = item;
			}
		}
		return cartItem;
	}

	private  int totalItems(Set<CartItem> cartItems) {

		int totalItems = cartItems.size();

		return totalItems;
	}

	private  double totalPrice(Set<CartItem> cartItems) {
		double totalPrice = 0.0;
		for (CartItem item : cartItems) {
			totalPrice += item.getTotalPrice();
		}
		return totalPrice;
	}

}
