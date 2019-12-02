package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.domain.Category;

public interface CategoryDao {
	
	public List<Category> findAll();
	public void add(Category category);
	public void remove(Category category);
	public void update(Category category);

}
