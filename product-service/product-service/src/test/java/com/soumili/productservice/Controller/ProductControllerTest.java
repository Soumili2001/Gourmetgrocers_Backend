package com.soumili.productservice.Controller;

import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumili.productservice.controller.ProductController;
import com.soumili.productservice.payloads.ProductDto;
import com.soumili.productservice.services.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@MockBean
	ProductService productService;

	@Autowired
	MockMvc mockMvc;

	
	@Test
	public void testViewProductById() throws Exception {
		ProductDto productdto = new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","");
		when(productService.viewProductById(11)).thenReturn(productdto);
		mockMvc.perform(get("/products/Id/{product_id}", 11).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.product_name").value("Carrots"));

	}

	@Test
	public void testViewAllProduct() throws Exception {
		List<ProductDto> productdtoList = Arrays.asList(
				new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables",""),
				new ProductDto(12, "Brinjal", 60, 90, "This is the good brinjal", "Vegetables",""),
				new ProductDto(13, "Cauliflower", 80, 100, "This is the good cauliflower", "Vegetables",""));
		when(productService.allProducts()).thenReturn(productdtoList);
		mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk()).andExpect(jsonPath("$[0].product_name").value("Carrots"));

	}

	@Test
	public void testCreateProduct() throws Exception {
		ProductDto productdto = new ProductDto(12, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","img");
		doNothing().when(productService).createProduct(productdto, "admin");
		mockMvc.perform(post("/products/{username}", "admin").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(productdto))).andExpect(status().isOk());

	}

	@Test
	public void testViewProductByName() throws Exception {
		List<ProductDto> productdtoList = Arrays.asList(
				new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables",""),
				new ProductDto(12, "Brinjal", 60, 90, "This is the good brinjal", "Vegetables",""),
				new ProductDto(13, "Cauliflower", 80, 100, "This is the good cauliflower", "Vegetables",""));
		when(productService.viewProductByName("Vegetables")).thenReturn(productdtoList);
		mockMvc.perform(get("/products/{category_name}", "Vegetables").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].product_name").value("Carrots"));

	}
	@Test
	public void testDeleteProductById() throws Exception {

		doNothing().when(productService).deleteProduct(11, "admin");
		mockMvc.perform(delete("/products/{username}/{product_id}", "admin", 11).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testupdateProduct() throws Exception {
		ProductDto productdto = new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","bo");
		doNothing().when(productService).updateProduct(11, productdto, "admin");
		mockMvc.perform(put("/products/update/{username}/{product_id}", "admin", 11).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(productdto))).andExpect(status().isOk());

	}

}
