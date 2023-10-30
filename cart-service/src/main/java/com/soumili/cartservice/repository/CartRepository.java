package com.soumili.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soumili.cartservice.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

	@Query("select u from Cart u where u.username=:username")
	public Cart findByUsername(@Param("username")String username);

	
}
