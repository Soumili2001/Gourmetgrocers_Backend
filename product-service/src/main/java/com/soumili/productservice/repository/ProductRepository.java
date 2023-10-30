package com.soumili.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.soumili.productservice.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

	
	@Query("select n from Product n where n.category_name=:category_name")
	public List<Product> getProductByName(@Param("category_name") String category_name);
	
	@Query("select p from Product p ORDER BY p.product_price DESC,p.stock_count")
    List<Product> filterProductsByPriceAndStock();
	
	@Query("select p from Product p where p.product_name=:product_name")
	public Product findProductByName(@Param("product_name")String product_name);
	
	public Product findById(int product_id);
	
	public void delete(Product p);
}


