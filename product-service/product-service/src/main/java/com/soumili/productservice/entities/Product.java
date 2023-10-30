package com.soumili.productservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int product_id;
	@NotBlank(message = "This field cannot be empty")
	private String product_name;
	@Min(value = 1, message = "Product price must be greater or equal to 1")
    @Max(value = 999999, message = "Product prices must be less than or equal to 999999")
	private double product_price;
	@Min(value = 1, message = "Stock Count must be greater or equal to 1")
    @Max(value = 999, message = "Stock Count must be less than or equal to 999")
	private int stock_count;
	@NotBlank(message = "This field cannot be empty")
	private String product_desc;
	@NotBlank(message = "This field cannot be empty")
	private String category_name;

	@NotBlank(message = "This field cannot be empty")
	private String product_img;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Product(int product_id, @NotBlank(message = "This field cannot be empty") String product_name,
			@Min(value = 1, message = "Product price must be greater or equal to 1") @Max(value = 999999, message = "Product prices must be less than or equal to 999999") double product_price,
			@Min(value = 1, message = "Stock Count must be greater or equal to 1") @Max(value = 999, message = "Stock Count must be less than or equal to 999") int stock_count,
			@NotBlank(message = "This field cannot be empty") String product_desc,
			@NotBlank(message = "This field cannot be empty") String category_name,
			@NotBlank(message = "This field cannot be empty") String product_img) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.stock_count = stock_count;
		this.product_desc = product_desc;
		this.category_name = category_name;
		this.product_img = product_img;
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

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	
	
}
