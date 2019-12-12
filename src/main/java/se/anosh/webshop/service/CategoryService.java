package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;

public interface CategoryService {
	
	public List<Category> findAll();
	public Category findById(int id) throws CategoryNotFoundException;
}
