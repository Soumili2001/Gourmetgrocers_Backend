package com.soumili.userservice.payloads;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {

	@NotBlank(message="This field cannot be empty")
	private String username;
	@NotBlank(message="This field cannot be empty")
	private String password;
	
	public LoginDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginDto(@NotBlank(message="This field cannot be empty")String username,@NotBlank(message="This field cannot be empty") String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

