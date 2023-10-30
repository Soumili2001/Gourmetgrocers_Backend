package com.soumili.productservice.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.soumili.productservice.Exception.ResourceNotFoundException;
import com.soumili.productservice.entities.Product;
import com.soumili.productservice.payloads.ProductDto;
import com.soumili.productservice.payloads.User;
import com.soumili.productservice.repository.ProductRepository;
import com.soumili.productservice.services.ProductService;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productrepo;
	
	@MockBean
	private RestTemplate resttemplate;
	
	@Test
	public void getallProducts() {
		when(productrepo.filterProductsByPriceAndStock()).thenReturn(List.of(new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","")));
		final var products=productService.allProducts();
		assertEquals(products.get(0).getProduct_name(),new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","").getProduct_name());
		
	}
	@Test
	public void getProductById() {
		when(productrepo.findById(11)).thenReturn(new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables",""));
		final var product=productService.viewProductById(11);
		assertEquals(product.getProduct_id(),new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","").getProduct_id());
		
	}
	@Test
	public void getProductByName() {
		when(productrepo.getProductByName("vegetables")).thenReturn(List.of(new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","")));
		final var product=productService.viewProductByName("vegetables");
		assertEquals(product.get(0).getProduct_name(),new ProductDto(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","").getProduct_name());
		
	}
	@Test
	public void getProductByNameException() {
		when(productrepo.getProductByName("vegetablesss")).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class,() ->productService.viewProductByName("vegetablesss"));
		
	}
	@Test
	public void deleteProductByCategoryName() {
		String category_name="vegetables";
		Product p=new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","");
		when(productrepo.getProductByName("vegetables")).thenReturn(List.of(p));
		
		
		productService.deleteByCategoryName(category_name);
		
		verify(productrepo,times(1)).delete(p);
	}
	@Test
	public void decreaseStockCount() {
		
		Product p=new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","");
		when(productrepo.findById(11)).thenReturn(p);
		
		
		productService.decreseStockCount(11);
		
		verify(productrepo,times(1)).save(p);
	}
	@Test
	public void updateStockCount() {
		
		Product p=new Product(11, "Carrots", 55.6, 80, "This is the good carrots", "Vegetables","");
		when(productrepo.findById(11)).thenReturn(p);
		
		
		productService.updateStockCount(11);
		
		verify(productrepo,times(1)).save(p);
	}
	
	
}
