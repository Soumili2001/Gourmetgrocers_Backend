package com.soumili.productservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.soumili.productservice.Exception.ResourceNotFoundException;
import com.soumili.productservice.Exception.UserNotAllowedException;
import com.soumili.productservice.Exception.UserNotLoggedInException;
import com.soumili.productservice.entities.Product;
import com.soumili.productservice.payloads.CategoryDto;
import com.soumili.productservice.payloads.ProductDto;
import com.soumili.productservice.payloads.User;
import com.soumili.productservice.repository.ProductRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInter{
	@Autowired
	private final ProductRepository productrepo;

	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private final ModelMapper modelMapper;
	
	Logger logger=LoggerFactory.getLogger(ProductService.class);

	@Override
	public void createProduct(ProductDto p, String username) {
		User user = getUser(username);
		if (user != null && user.getUserRole().equalsIgnoreCase("Admin")) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			} else {
				
					CategoryDto cat=getcategory(p.getCategory_name());
					if (productrepo.findProductByName(p.getProduct_name()) != null) {
						throw new ResourceNotFoundException("Product Already Exists");}
					else {
					Product entity = modelMapper.map(p, Product.class);
					productrepo.save(entity);
					logger.info("The product is successfully created");
					}
				} 

			}
		 else {
			throw new UserNotAllowedException();
		}
	}

	@Override
	public void updateProduct(int product_id, ProductDto newProduct, String username) {
		User user = getUser(username);

		if (user != null && user.getUserRole().equalsIgnoreCase("Admin")) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			} else {
				Product oldProduct = productrepo.findById(product_id);
				    CategoryDto cat=getcategory(newProduct.getCategory_name());
				    String name=oldProduct.getProduct_name();
				    if (productrepo.findProductByName((newProduct.getProduct_name())) != null && !(newProduct.getProduct_name().equalsIgnoreCase(name))) {
						throw new ResourceNotFoundException("Product Already Exists");}
					oldProduct.setProduct_name(newProduct.getProduct_name());
					oldProduct.setProduct_price(newProduct.getProduct_price());
					oldProduct.setProduct_desc(newProduct.getProduct_desc());
					oldProduct.setCategory_name(newProduct.getCategory_name());
					oldProduct.setStock_count(newProduct.getStock_count());
					oldProduct.setProduct_img(newProduct.getProduct_img());
					productrepo.save(oldProduct);

				} 
			
		} else

		{
			
			throw new UserNotAllowedException();
			
		}
	}

	@Override
	public void deleteProduct(int product_id, String username) {
		User user = getUser(username);
		logger.debug("Entering delete Product endpoint");
		if (user != null && user.getUserRole().equalsIgnoreCase("Admin")) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			} else {
				logger.debug("Entering delete product endpoint");
				Product p = productrepo.findById(product_id);
				productrepo.delete(p);
			
				
			}
		} else {
			throw new UserNotAllowedException();
		}
	}

	@Override
	public void deleteByCategoryName(String category_name) {
		List<Product> products = productrepo.getProductByName(category_name);
		for (Product p : products) {
			productrepo.delete(p);
		}
		logger.info("All products by category name "+category_name +" are successfully deleted");
	}

	@Override
	public List<ProductDto> viewProductByName(String category_name) {
		try {
			restTemplate.getForObject("http://CATEGORY-SERVICE/categories/" + category_name, CategoryDto.class);
			List<Product> p = productrepo.getProductByName(category_name);

			List<ProductDto> allproducts = p.stream().map(c -> this.modelMapper.map(c, ProductDto.class))
					.collect(Collectors.toList());
			logger.info("All products by category name "+category_name +" are fetched");
			return allproducts;
			
		}

		catch (Exception e) {

			logger.error("Unable to find catagory from this name "+category_name +" message "+e.getMessage() ,e);
			throw new ResourceNotFoundException("This Category does not exists: " + category_name);
		}
	}

	@Override
	public List<ProductDto> allProducts() {

		List<Product> products = productrepo.filterProductsByPriceAndStock();
		List<ProductDto> viewall = products.stream().map(c -> this.modelMapper.map(c, ProductDto.class))
				.collect(Collectors.toList());
		
		return viewall;
	}

	@Override
	public ProductDto viewProductById(int product_id) {
		Product findProductById = productrepo.findById(product_id);
		ProductDto p = this.modelMapper.map(findProductById, ProductDto.class);
		
		return p;
	}

	@Override
	public void decreseStockCount(int product_id) {
		Product p = productrepo.findById(product_id);
		p.setStock_count(p.getStock_count() - 1);
		productrepo.save(p);

	}

	@Override
	public void updateStockCount(int product_id) {
		Product p = productrepo.findById(product_id);
		p.setStock_count(p.getStock_count() + 1);
		productrepo.save(p);

	}

	
	public User getUser(String username) {
		try {
			User user = restTemplate.getForObject("http://USER-SERVICE/user/" + username, User.class);
			return user;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This user is not yet registered!!.");
		}
	}
	public CategoryDto getcategory(String category_name) {
		try {
			CategoryDto cat = restTemplate.getForObject("http://CATEGORY-SERVICE/categories/" + category_name,CategoryDto.class);
			return cat;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This product cannot be updated as the category does not exists!!");
		}
	}
	

}
