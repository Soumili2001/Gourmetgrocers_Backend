package com.soumili.orderservice.payloads;

import jakarta.validation.constraints.NotBlank;

public class BillingResponse {

	@NotBlank(message = "This field cannot be empty")
	private String address;
	@NotBlank(message = "This field cannot be empty")
	private String paymentMethod;

	public BillingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BillingResponse(@NotBlank(message = "This field cannot be empty") String address,
			@NotBlank(message = "This field cannot be empty") String paymentMethod) {
		super();
		this.address = address;
		this.paymentMethod = paymentMethod;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
