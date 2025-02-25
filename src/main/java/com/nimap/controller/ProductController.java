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

import com.nimap.models.Product;
import com.nimap.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
    public ResponseEntity<?> getAllProductByPageNo( @RequestParam int page){
        return productService.getAllProductByPageNo(page);
    }
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody @Valid Product product){
		return productService.createProduct(product);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable int id){
		return productService.getProductById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProductById(@PathVariable int id,@RequestBody @Valid Product updatedProduct){
		return productService.updateProductById(id, updatedProduct);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById( @PathVariable int id){
        return productService.deleteProductById(id);
    }
	
	
	
	
}
