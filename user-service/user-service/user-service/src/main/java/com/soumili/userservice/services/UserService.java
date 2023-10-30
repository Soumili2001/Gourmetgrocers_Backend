package com.soumili.userservice.services;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soumili.userservice.exceptions.UserNotAllowedException;
import com.soumili.userservice.exceptions.UserNotLoggedInException;
import com.soumili.userservice.entities.User;
import com.soumili.userservice.exceptions.UserAlreadyExistsException;
import com.soumili.userservice.exceptions.ResourceNotFoundException;
import com.soumili.userservice.exceptions.WrongLoginCredentialsException;
import com.soumili.userservice.payloads.Cart;
import com.soumili.userservice.payloads.LoginDto;
import com.soumili.userservice.payloads.Order;
import com.soumili.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInter {

	@Autowired
	private final UserRepository userRepo;

	@Autowired
	private final RestTemplate restTemplate;
	
	Logger logger=LoggerFactory.getLogger(UserService.class);

	@Override
	public void registerUser(User user) {
		if (userRepo.findUserByUserName(user.getUsername()) == null
				&& userRepo.findUserByEmail(user.getEmail()) == null) {
			if (user.getUsername().equalsIgnoreCase("admin") && user.getEmail().equals("Admin123@gmail.com")) {
				user.setUserRole("Admin");
			} else {
			user.setUserRole("user");
			}
			userRepo.save(user);
			
		} else {
			throw new UserAlreadyExistsException();
		}
		logger.info("Newly created user is {} " ,user);
	}

	@Override
	public User loginUser(LoginDto logindto) {
		User user = userRepo.findUserByUserName(logindto.getUsername());
		if (user != null) {
			
			if (user.getPassword().equals(logindto.getPassword())) {
				user.setLoggedIn(true);
				User u=userRepo.save(user);
			    user=u;
			} else {
				logger.error("Wrong login credentials", user.getUsername());;
				throw new WrongLoginCredentialsException();
			}
		}

		else {
			throw new ResourceNotFoundException("User is not registered yet!!!");
		}
		logger.info("THe response is {} ",user);
		return user;

	}

	@Override
	public void logoutUser(String username) {
		User user = userRepo.findUserByUserName(username);
		if (user != null) {
			if (user != null && user.isLoggedIn()) {
				user.setLoggedIn(false);
				userRepo.save(user);
			} 
		} else {
			
			throw new ResourceNotFoundException("User is not registered yet!!!");
			
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUsername(String username) {
		User user = userRepo.findUserByUserName(username);
		if (user != null) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			}
			Cart cart = restTemplate.getForObject("http://CART-SERVICE/cart/" + username, Cart.class);
			user.setCart(cart);

			List<Order> order = restTemplate.getForObject("http://ORDER-SERVICE/orders/" + username, List.class);
			user.setOrder(order);
			logger.info("THe response is {} ",user);
			return user;

		} else {
			logger.error("User not registered");
			throw new ResourceNotFoundException("User is not registered yet!!!");
		}
		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAllUsers(String username) {

		User user = userRepo.findUserByUserName(username);

		if (user != null) {
			if (!(user.getUserRole().equalsIgnoreCase("Admin"))) {
				throw new UserNotAllowedException();
			}
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			}
			List<User> all = userRepo.findAll();
			for (User u : all) {

				Cart cart = restTemplate.getForObject("http://CART-SERVICE/cart/" + u.getUsername(), Cart.class);
				u.setCart(cart);

				List<Order> order = restTemplate.getForObject("http://ORDER-SERVICE/orders/" + u.getUsername(),
						List.class);
				u.setOrder(order);
			}
			
			return all;
		} else {
			throw new ResourceNotFoundException("User is not registered yet!!!");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUser(String username) {
		User user = userRepo.findUserByUserName(username);
		if (user != null) {

		Cart cart = restTemplate.getForObject("http://CART-SERVICE/cart/" + username, Cart.class);
		user.setCart(cart);

		List<Order> order = restTemplate.getForObject("http://ORDER-SERVICE/orders/" + username, List.class);
		user.setOrder(order);
		logger.info("THe response is {} ",user);
		return user;}
		else {
			logger.error("User not registered");
			throw new ResourceNotFoundException("User is not registered yet!!!");
		}
		
	}
}