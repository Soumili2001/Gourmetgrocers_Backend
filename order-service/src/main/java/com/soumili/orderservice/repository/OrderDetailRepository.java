package com.soumili.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soumili.orderservice.entities.Order;
import com.soumili.orderservice.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	
}
