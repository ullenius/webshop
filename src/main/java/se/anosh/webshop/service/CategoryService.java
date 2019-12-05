package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Person;

public interface CategoryService {
	
	public List<Category> findAll();
	public Category findById(int id) throws CategoryNotFoundException;
	public void addCategory(Category newCategory);
	public void removeCategory(Category category);
	public void updateCategory(Category category);

}
