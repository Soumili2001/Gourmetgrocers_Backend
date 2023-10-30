package com.soumili.categoryservice.payloads;


import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
	private int category_id;
	@NotBlank(message = "This field cannot be empty")
	private String category_name;
	@NotBlank(message = "This field cannot be empty")
	private String category_desc;
	
	@NotBlank(message = "This field cannot be empty")
	private String cat_img;

	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryDto(int category_id, String category_name, String category_desc,String cat_img) {
		super();
		this.category_id = category_id;
		this.category_name = category_name;
		this.category_desc = category_desc;
		this.cat_img = cat_img;
}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_desc() {
		return category_desc;
	}

	public void setCategory_desc(String category_desc) {
		this.category_desc = category_desc;
	}

	public String getCat_img() {
		return cat_img;
	}

	public void setCat_img(String cat_img) {
		this.cat_img = cat_img;
	}

}
