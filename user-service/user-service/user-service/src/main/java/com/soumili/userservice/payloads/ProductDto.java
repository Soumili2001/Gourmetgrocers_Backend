package com.soumili.userservice.payloads;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDto {

	private int product_id;
	@NotBlank(message = "This field cannot be empty")
	private String product_name;
	@NotNull(message = "This field cannot be null")
	private double product_price;
	@NotNull(message = "This field cannot be null")
	private Integer stock_count;
	@NotBlank(message = "This field cannot be empty")
	private String product_desc;
	@NotBlank(message = "This field cannot be empty")
	private String category_name;
	@NotBlank(message = "This field cannot be empty")
	private String product_img;

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto(int product_id, String product_name, double product_price, Integer stock_count,
			String product_desc, String category_name,String product_img) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.stock_count = stock_count;
		this.product_desc = product_desc;
		this.category_name = category_name;
		this.product_img=product_img;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}

	public Integer getStock_count() {
		return stock_count;
	}

	public void setStock_count(Integer stock_count) {
		this.stock_count = stock_count;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getProduct_imgset() {
		return product_img;
	}

	public void setProduct_imgset(String product_img) {
		this.product_img = product_img;
	}
}
