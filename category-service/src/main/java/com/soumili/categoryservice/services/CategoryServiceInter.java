package com.soumili.categoryservice.services;

import java.util.List;

import com.soumili.categoryservice.payloads.CategoryDto;

public interface CategoryServiceInter {

	public void createcategory(CategoryDto categorydto, String username);

	public void updatecategory(CategoryDto categorydto, String category_name, String username);

	public void deletecategory(String category_name, String username);

	public CategoryDto getById(int category_id);

	public List<CategoryDto> allcategories();

	public CategoryDto findCategory(String category_name);

	public CategoryDto findCategoryByUsername(String category_name);
}
