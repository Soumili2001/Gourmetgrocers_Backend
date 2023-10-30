package com.soumili.categoryservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.soumili.categoryservice.Exception.CategoryAlreadyExistsException;
import com.soumili.categoryservice.Exception.ResourceNotFoundException;
import com.soumili.categoryservice.Exception.UserNotAllowedException;
import com.soumili.categoryservice.Exception.UserNotLoggedInException;
import com.soumili.categoryservice.entities.Category;
import com.soumili.categoryservice.payloads.CategoryDto;
import com.soumili.categoryservice.payloads.User;
import com.soumili.categoryservice.repository.CategoryRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService implements CategoryServiceInter {

	@Autowired
	private final CategoryRepository categoryrepo;
	@Autowired
	private final ModelMapper mapper;

	@Autowired
	private  final RestTemplate restTemplate;
	
	Logger logger=LoggerFactory.getLogger(CategoryService.class);


	@Override
	public void createcategory(CategoryDto categorydto, String username) {
		User user = getUser(username);
		if (!user.getUserRole().equalsIgnoreCase("Admin")) {
			throw new UserNotAllowedException();
		}

		if (!user.isLoggedIn()) {
			throw new UserNotLoggedInException();
		} else if (categoryrepo.findCategoryByName(categorydto.getCategory_name()) != null) {
			throw new CategoryAlreadyExistsException();
		} else {
			Category c = mapper.map(categorydto, Category.class);
			Category newcategory = categoryrepo.save(c);
			mapper.map(newcategory, CategoryDto.class);
		}
		logger.info("New category is created with category");
	}

	@Override
	public void updatecategory(CategoryDto categorydto, String category_name, String username) {
		User user = getUser(username);
		if (user != null && user.getUserRole().equalsIgnoreCase("Admin")) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			} else {

				Category oldcategory = categoryrepo.findCategoryByName(category_name);
				if (oldcategory != null) {
					String name=oldcategory.getCategory_name();
					if ((categoryrepo.findCategoryByName(categorydto.getCategory_name())) != null && !(categorydto.getCategory_name().equalsIgnoreCase(name))) {
						throw new CategoryAlreadyExistsException();
					}
					oldcategory.setCategory_name(categorydto.getCategory_name());
					oldcategory.setCategory_desc(categorydto.getCategory_desc());
					oldcategory.setCat_img(categorydto.getCat_img());
					categoryrepo.save(oldcategory);
					mapper.map(oldcategory, CategoryDto.class);
					restTemplate.delete("http://PRODUCT-SERVICE/products/delete/" + category_name);
				} else {
					throw new ResourceNotFoundException("This category does not exists");
				}
			}
		} else {
			throw new UserNotAllowedException();
		}
	}

	@Override
	public void deletecategory(String category_name, String username) {
		User user = getUser(username);
		if (user != null && user.getUserRole().equalsIgnoreCase("Admin")) {
			if (!user.isLoggedIn()) {
				throw new UserNotLoggedInException();
			} else {
				Category getcategory = categoryrepo.findCategoryByName(category_name);
				if (getcategory != null) {
					categoryrepo.delete(getcategory);
					restTemplate.delete("http://PRODUCT-SERVICE/products/delete/" + category_name);
				} else {
					throw new ResourceNotFoundException("This category does not exists");
				}
			}
		} else {
			throw new UserNotAllowedException();
		}
	}

	@Override
	public CategoryDto getById(int category_id) {
		Category getcategory = categoryrepo.findById(category_id)
				.orElseThrow(() -> new ResourceNotFoundException("This id does not exists " + category_id));
		CategoryDto c = mapper.map(getcategory, CategoryDto.class);

		return c;

	}

	@Override
	public List<CategoryDto> allcategories() {
		List<Category> findall = categoryrepo.findAll();
		List<CategoryDto> viewall = findall.stream().map(c -> this.mapper.map(c, CategoryDto.class))
				.collect(Collectors.toList());
		return viewall;
	}

	@Override
	public CategoryDto findCategory(String category_name) {
		Category c = categoryrepo.findCategoryByName(category_name);

		return mapper.map(c, CategoryDto.class);

	}

	@Override
	public CategoryDto findCategoryByUsername(String category_name) {
		Category c = categoryrepo.findCategoryByName(category_name);
		if (c != null) {
			return mapper.map(c, CategoryDto.class);
		} else {
			throw new ResourceNotFoundException("This Category does not exists: " + category_name);
		}

	}

	public User getUser(String username) {
		try {
			User user = restTemplate.getForObject("http://USER-SERVICE/user/" + username, User.class);
			return user;
		} catch (Exception e) {
			throw new ResourceNotFoundException("This user is not yet registered.");
		}

	}
}
