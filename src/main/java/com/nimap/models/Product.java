package com.nimap.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull(message = "name column must not be null")
	@Size(min = 3 , max = 20 , message="name must be between 3 to 20 characters")
	@Column(unique = true , nullable = false)
	private String name;
	
	@Size(min = 3 , max = 50 , message="name must be between 3 to 50 characters")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	
}
