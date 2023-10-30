package com.soumili.cartservice.services;

import com.soumili.cartservice.entities.Cart;
import com.soumili.cartservice.payloads.ItemResponse;

public interface CartServiceInter {

	public Cart addItemToCart(ItemResponse itemResponse, String username);

	public Cart decreaseItemInCart(ItemResponse itemResponse, String username);

	public void deleteItemFromCart(int productId, String username);

	public void deleteCartById(int cart_id);

	public Cart getCartByUsername(String username);

	public Cart getCartById(String username, int cart_id);

}
