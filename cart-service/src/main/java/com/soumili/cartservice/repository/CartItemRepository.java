package com.soumili.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soumili.cartservice.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
