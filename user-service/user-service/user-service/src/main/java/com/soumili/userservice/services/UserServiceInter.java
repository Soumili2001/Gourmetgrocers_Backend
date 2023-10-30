package com.soumili.userservice.services;

import java.util.List;

import com.soumili.userservice.entities.User;
import com.soumili.userservice.payloads.LoginDto;

public interface UserServiceInter {
	public void registerUser(User user);

	public User loginUser(LoginDto logindto);

	public void logoutUser(String username);

	public User findUserByUsername(String username);

	public List<User> findAllUsers(String username);

	public User findUser(String username);

}
