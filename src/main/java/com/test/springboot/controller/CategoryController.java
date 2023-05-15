package com.test.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.springboot.model.Category;
import com.test.springboot.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/create")
	public Category createCategory (@RequestBody Category category) {
		categoryService.createCategory(category);
		return category;
	}
	
	@GetMapping("/list")
	public List<Category> listCategory () {
		List<Category>  listCate = categoryService.listCategory();
		return listCate;
	}
	
	@PutMapping("/update/{id}")
	public String updateCategory (@PathVariable Long id, @RequestBody Category category) {
		return categoryService.updateCategory(id,category);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteCategory (@PathVariable Long id) {
		return categoryService.deleteCategory(id);
	}
	
	@PostMapping("/login")
	public String login (@RequestBody Category category) {
		return categoryService.login(category);
	}
	
	@GetMapping("/giaima/{token}")
	public Category giaima (@PathVariable String token) {
		try {
			return categoryService.giaima(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
