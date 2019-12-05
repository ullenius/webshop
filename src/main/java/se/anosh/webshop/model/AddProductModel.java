package se.anosh.webshop.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import se.anosh.webshop.domain.Category;

// This is a backing bean
public class AddProductModel {
	
	private String name;
	private String price;
	private String category;
	
	private List<Category> categories; // for displaying using thymeleaf
	
	public AddProductModel() {
		categories = Collections.emptyList();
	}

	public AddProductModel(String name, String price, String category) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		categories = Collections.emptyList();
	}
	
	public AddProductModel(List<Category> categories) {
		this.categories = Objects.requireNonNull(categories);
	}
	
	public List<Category> getCategories() {
		return categories;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
