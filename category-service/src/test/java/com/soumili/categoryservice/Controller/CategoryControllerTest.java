package com.soumili.categoryservice.Controller;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soumili.categoryservice.controller.CategoryController;
import com.soumili.categoryservice.entities.Category;
import com.soumili.categoryservice.payloads.CategoryDto;
import com.soumili.categoryservice.services.CategoryService;




@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	

		@MockBean
		CategoryService categoryService;

		@Autowired
		MockMvc mockMvc;

		@Test
		public void testcreateCategory() throws Exception {
			CategoryDto cat= new CategoryDto(1,"fruits","This is fruits section","img");
				
			doNothing().when(categoryService).createcategory(cat,"admin");
			mockMvc.perform(post("/categories/{username}","admin").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(cat))).andExpect(status().isOk());

		}
		@Test
		public void testupdateCategory() throws Exception {
			CategoryDto cat= new CategoryDto(1,"fruits","This is fruits section","img");
			doNothing().when(categoryService).updatecategory(cat,"fruits","admin");
			mockMvc.perform(put("/categories/{username}/{category_name}", "admin","fruits").contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(cat))).andExpect(status().isOk());

		}
		@Test
		public void testDeleteCategoryByName() throws Exception {

			doNothing().when(categoryService).deletecategory("fruits","admin");
			mockMvc.perform(delete("/categories/{username}/{category_name}", "admin","fruits").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

		}
		@Test
		public void testViewCategoryById() throws Exception {
			CategoryDto cat= new CategoryDto(1,"fruits","This is fruits section","img");
			when(categoryService.getById(1)).thenReturn(cat);
			mockMvc.perform(get("/categories/Id/{category_name}", 1).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andExpect(jsonPath("$.category_name").value("fruits"));

		}

		@Test
		public void testViewAllCategory() throws Exception {
			List<CategoryDto> cat = List.of(new CategoryDto(1,"fruits","This is fruits section","img"));
			when(categoryService.allcategories()).thenReturn(cat);
			mockMvc.perform(get("/categories").contentType(MediaType.APPLICATION_JSON))

					.andExpect(status().isOk()).andExpect(jsonPath("$[0].category_name").value("fruits"));

		}

		@Test
		public void testViewCategoryByName() throws Exception {
			CategoryDto cat= new CategoryDto(1,"fruits","This is fruits section","img");
			when(categoryService.findCategoryByUsername("fruits")).thenReturn(cat);
			mockMvc.perform(get("/categories/{category_name}", "fruits").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

		}
		@Test
		public void testFindCategoryByName() throws Exception {
			CategoryDto cat= new CategoryDto(1,"fruits","This is fruits section","img");
			when(categoryService.findCategory("fruits")).thenReturn(cat);
			mockMvc.perform(get("/categories/find/{category_name}", "fruits").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());

		}
		




}
