package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;

public interface CategoryDao {
	
	public List<Category> findAll();
	public Category findById(int id) throws CategoryNotFoundException;
}
