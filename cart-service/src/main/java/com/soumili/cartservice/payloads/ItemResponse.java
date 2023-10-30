package com.soumili.cartservice.payloads;

import jakarta.validation.constraints.NotNull;

public class ItemResponse {
	@NotNull(message = "This field cannot be empty")
	private Integer product_id;
	@NotNull(message = "This field cannot be empty")
	private Integer product_quantity;

	public ItemResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemResponse(@NotNull(message = "This field cannot be empty") int product_id,
			@NotNull(message = "This field cannot be empty") int product_quantity) {
		super();
		this.product_id = product_id;
		this.product_quantity = product_quantity;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

}
