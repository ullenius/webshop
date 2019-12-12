package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;

public interface ProductDao {
	
	public List<Product> findAll();
	public List<Product> findByCategory(Category category);
	public Product findById(int id) throws ProductNotFoundException;
	public void add(Product item);
}
