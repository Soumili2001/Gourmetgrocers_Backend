package com.soumili.userservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soumili.userservice.entities.User;
import com.soumili.userservice.payloads.ApiResponse;
import com.soumili.userservice.payloads.LoginDto;
import com.soumili.userservice.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	 @PostMapping("/register")
	    public  ApiResponse register(@Valid @RequestBody  User user) {
	        userService.registerUser(user);
	        return new ApiResponse("User registration is successful!");
	    }

	    
	    @PostMapping("/login")
	    public User login(@Valid @RequestBody LoginDto logindto) {
	        User user=userService.loginUser(logindto);
	        return user;
	    }

	   
	    @PostMapping("/logout/{username}")
	    public  ApiResponse logout(@PathVariable(name = "username") String username) {
            logger.debug("Entering logout functionality endpoint");
	        userService.logoutUser(username);
	        return new ApiResponse("Logout successful!");
	    }
	   
	    @GetMapping("/{username}")
	    public User get(@PathVariable(name = "username")  String username) {
	        return userService.findUser(username);
	    }
	    
	    @GetMapping("/find/{username}")
	    public User findUser(@PathVariable(name = "username")  String username) {
	        return userService.findUserByUsername(username);
	    }

	   
	    @GetMapping("/All/{username}")
	    public List<User> getAll(@PathVariable("username") String username) {
	        List<User> all= userService.findAllUsers(username);
	        logger.info("Successfully fetched all users {}",all);
	        return all;
	    }

}
