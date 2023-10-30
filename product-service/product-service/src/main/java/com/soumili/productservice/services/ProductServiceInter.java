package com.soumili.productservice.services;

import java.util.List;

import com.soumili.productservice.payloads.ProductDto;

public interface ProductServiceInter {

	public void createProduct(ProductDto p, String username);

	public void updateProduct(int product_id, ProductDto newProduct, String username);

	public void deleteProduct(int product_id, String username);

	public void deleteByCategoryName(String category_name);

	public List<ProductDto> viewProductByName(String category_name);

	public List<ProductDto> allProducts();

	public ProductDto viewProductById(int product_id);

	public void decreseStockCount(int product_id);

	public void updateStockCount(int product_id);

}
