package com.soumili.categoryservice.repository;



//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soumili.categoryservice.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

	
	
	@Query("select n from Category n where n.category_name=:category_name")
	public Category findCategoryByName(@Param("category_name")String category_name);
}
