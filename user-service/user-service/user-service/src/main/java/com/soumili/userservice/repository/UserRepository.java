package com.soumili.userservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soumili.userservice.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	@Query("select u from User u where u.username=:username")
	public User findUserByUserName(@Param("username")String username);
	
	
	@Query("select u from User u where u.email=:email")
	public User findUserByEmail(@Param("email")String email);
	
}
