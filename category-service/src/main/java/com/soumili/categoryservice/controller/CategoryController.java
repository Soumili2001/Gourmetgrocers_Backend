package com.soumili.categoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.soumili.categoryservice.payloads.ApiResponse;
import com.soumili.categoryservice.payloads.CategoryDto;
import com.soumili.categoryservice.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryservice;

	@PostMapping("/{username}")
	public  ApiResponse  create(@Valid @RequestBody CategoryDto categorydto,@PathVariable  String username) {
		categoryservice.createcategory(categorydto,username);
		return new ApiResponse("New Category is created!");
	}
    @PutMapping("/{username}/{category_name}")
	public  ApiResponse  update(@Valid @RequestBody CategoryDto categorydto,@PathVariable String category_name,@PathVariable  String username) {
		categoryservice.updatecategory(categorydto,category_name,username);
		return new ApiResponse("Category is updated!");	}
	
    @DeleteMapping("/{username}/{category_name}")
	public @ResponseBody ApiResponse delete(@PathVariable String category_name,@PathVariable String username){
		categoryservice.deletecategory(category_name,username);

		return new ApiResponse("The category is deleted");
	}
	
    @GetMapping("/Id/{category_id}")
	public ResponseEntity<CategoryDto> getById(@PathVariable int category_id){
		CategoryDto getById=categoryservice.getById(category_id);
		return new ResponseEntity<CategoryDto>(getById,HttpStatus.OK);
	}
	
    @GetMapping
	public ResponseEntity<List<CategoryDto>>getall(){
		List<CategoryDto> getall=categoryservice.allcategories();
		return new ResponseEntity<List<CategoryDto>>(getall,HttpStatus.OK);
		
	}
    @GetMapping("/{category_name}")
    public  CategoryDto getCategory(@PathVariable String category_name){
		return categoryservice.findCategory(category_name);

		
	}
    @GetMapping("/find/{category_name}")
    public  CategoryDto getCategoryByName(@PathVariable String category_name){
		return categoryservice.findCategoryByUsername(category_name);

		
	}
    
    

}