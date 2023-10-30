package com.soumili.productservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.soumili.productservice.payloads.ApiResponse;
import com.soumili.productservice.payloads.ProductDto;
import com.soumili.productservice.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	Logger logger=LoggerFactory.getLogger(ProductController.class);

	@PostMapping("/{username}")
	public  ApiResponse createNewProduct(@Valid @RequestBody ProductDto p, @PathVariable String username) {
		productService.createProduct(p, username);
		
		return new ApiResponse("New Product is created!");
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> viewAllProduct() {

		List<ProductDto> allProduct = productService.allProducts();
		logger.info("All products are succesfully fetched");
		return new ResponseEntity<List<ProductDto>>(allProduct, HttpStatus.OK);
	}

	@GetMapping("/Id/{product_id}")
	public ResponseEntity<ProductDto> findProductById(@PathVariable int product_id) {
		ProductDto viewproductbyid = productService.viewProductById(product_id);
		logger.info("Product with Id "+product_id+" is fetched");
		return new ResponseEntity<ProductDto>(viewproductbyid, HttpStatus.OK);
	}

	@GetMapping("/{category_name}")
	public ResponseEntity<List<ProductDto>> findByCategoryName(@PathVariable String category_name) {
		List<ProductDto> allProduct = productService.viewProductByName(category_name);
		return new ResponseEntity<List<ProductDto>>(allProduct, HttpStatus.OK);

	}

	@DeleteMapping("/{username}/{product_id}")
	public @ResponseBody ApiResponse deleteProduct(@PathVariable int product_id, @PathVariable String username) {
		productService.deleteProduct(product_id, username);
		logger.warn("Product is deleted with product ID "+product_id);
		return new ApiResponse("Product is deleted!");

	}

	@DeleteMapping("/delete/{category_name}")
	public void deleteProductByCategory(@PathVariable String category_name) {
		productService.deleteByCategoryName(category_name);

	}

	@PutMapping("/update/{username}/{product_id}")
	public ApiResponse updateProduct( @PathVariable int product_id, @Valid @RequestBody ProductDto newProduct,
			@PathVariable String username) {
		productService.updateProduct(product_id, newProduct, username);
		logger.info("Product with Id "+product_id+" is successfully updated");
		return new ApiResponse("Product is updated!");

	}

	@PutMapping("/decrease/{product_id}")
	public void decreaseStockCount(@PathVariable("product_id") int product_id) {
		productService.decreseStockCount(product_id);
	}
	@PutMapping("/update/{product_id}")
	public void StockCount(@PathVariable("product_id") int product_id) {
		productService.updateStockCount(product_id);
	}
}
