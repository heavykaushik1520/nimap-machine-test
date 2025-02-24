package com.nimap.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull(message = "name is mandatory")
	@Size(min = 3 , max = 20 , message = "name must be between 3 to 20 characters")
	private String name;
	
	@NotNull(message = "name is mandatory")
	@Size(min = 3 , max = 50 , message = "name must be between 3 to 50 characters")
	private String description;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Product> productList;
	

}
