package com.nimap.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nimap.models.Category;
import com.nimap.repository.CategoryRepository;
import com.nimap.responewrapper.ResponseWrapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CategoryService {

	private static final int DEFAULT_PAGE_SIZE = 3;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ResponseWrapper responseWrapper;

	public ResponseEntity<?> createCategory(Category category) {
		Category savedCategory = categoryRepository.save(category);
		responseWrapper.setMessage("Category created Successfully");
		responseWrapper.setData(savedCategory);
		return new ResponseEntity<>(responseWrapper, HttpStatus.CREATED);
	}

	public ResponseEntity<?> getCategoryById(int id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with given id : " + id);
		});
		responseWrapper.setMessage("Category found with id : " + id);
		responseWrapper.setData(category);
		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

	public ResponseEntity<?> deleteCategoryById(int id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with given id : " + id);
		});
		categoryRepository.deleteById(id);
		responseWrapper.setMessage("Category Deleted Successfully ");
		responseWrapper.setData(null);
		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);

	}

	public ResponseEntity<?> updateCategoryById(int id, Category updatedCategory) {
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with id : " + id));

		category.setName(updatedCategory.getName());
		category.setDescription(updatedCategory.getDescription());

		Category upCategory = categoryRepository.save(category);

		responseWrapper.setMessage("Category updated successfully");
		responseWrapper.setData(upCategory);

		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

	public ResponseEntity<?> getCategories(int page) {
		Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE, Sort.by("id").ascending());
		Page<Category> categoryPage = categoryRepository.findAll(pageable);
		
		if (page <= 0) {
			responseWrapper.setMessage("No page at this number");
			responseWrapper.setData(null);
			return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
		}
		
		if (categoryPage.isEmpty()) {
			responseWrapper.setMessage("No categories found for the specified page");
			responseWrapper.setData(null);
			return new ResponseEntity<>(responseWrapper, HttpStatus.NOT_FOUND);
		}
		responseWrapper.setMessage("Fetched categories");
		responseWrapper.setData(categoryPage.getContent());
		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

}
