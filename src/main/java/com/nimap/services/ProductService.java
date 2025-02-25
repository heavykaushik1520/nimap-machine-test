package com.nimap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nimap.models.Category;
import com.nimap.models.Product;
import com.nimap.repository.CategoryRepository;
import com.nimap.repository.ProductRepository;
import com.nimap.responewrapper.ResponseWrapper;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ResponseWrapper responseWrapper;

	private final static int PAGE_SIZE = 5;

	public ResponseEntity<?> getAllProductByPageNo(int page) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").ascending());
		Page<Product> products = productRepository.findAll(pageable);
		if (page <= 0) {
			responseWrapper.setMessage("Bad Request");
			responseWrapper.setData(null);
			return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
		} else if (products.isEmpty()) {
			responseWrapper.setMessage("No products");
			responseWrapper.setData(null);
			return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
		} else {
			responseWrapper.setMessage("Products");
			responseWrapper.setData(products.getContent());
			return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> createProduct(Product product) {
		Category category = categoryRepository.findById(product.getCategory().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No category found with id : " + product.getCategory().getId()));
		product.setCategory(category);
		Product savedProduct = productRepository.save(product);

		responseWrapper.setMessage("Successfully added to product");
		responseWrapper.setData(savedProduct);
		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

	public ResponseEntity<?> getProductById(int id) {
		Product product = productRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with id : " + id));

		responseWrapper.setMessage("Product searching successful");
		responseWrapper.setData(product);
		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

	public ResponseEntity<?> updateProductById(int id, Product updatedProduct) {

		Product foundProduct = productRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found with id : " + id));

		Category category = categoryRepository.findById(updatedProduct.getCategory().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"No category found with id : " + updatedProduct.getCategory().getId()));

		foundProduct.setName(updatedProduct.getName());

		foundProduct.setDescription(updatedProduct.getDescription());

		foundProduct.setCategory(category);

		Product savedProduct = productRepository.save(foundProduct);

		responseWrapper.setMessage("Product updated successfully");
		responseWrapper.setData(savedProduct);

		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}

	public ResponseEntity<?> deleteProductById(int id) {
		Product foundProduct = productRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found with id : " + id));

		productRepository.deleteById(id);

		responseWrapper.setMessage("Product successfully deleted");
		responseWrapper.setData(null);

		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);

	}

}
