package com.soumili.userservice.payloads;

public class ApiResponse {

	
	private String message;
	
	
	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiResponse(String message) {
		super();
		
		this.setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
