package com.soumili.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soumili.orderservice.entities.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {

	@Query("select u from Order u where u.username=:username ORDER BY u.orderDate DESC ")
	List<Order> findAllByUsername(@Param("username")String username);
	
	@Query("select u from Order u ORDER BY u.orderDate DESC ")
	List<Order> findAll();
}
