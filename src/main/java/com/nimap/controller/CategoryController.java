package com.nimap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimap.models.Category;
import com.nimap.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<?> createCategory(@RequestBody @Valid Category category) {
		return categoryService.createCategory(category);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable int id) {
		return categoryService.getCategoryById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable int id) {
		return categoryService.deleteCategoryById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategoryById(@PathVariable int id, @RequestBody Category updatedCategory) {
		return categoryService.updateCategoryById(id, updatedCategory);
	}

	@GetMapping
	public ResponseEntity<?> getCategories(@RequestParam int page) {
		return categoryService.getCategories(page);
	}
}
