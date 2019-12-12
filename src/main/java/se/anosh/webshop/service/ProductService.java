package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;


public interface ProductService {

	public List<Product> findAllProducts();
	public List<Product> findByName(String match);
	public List<Product> findByCategory(Category category);
	public Product findById(int id) throws ProductNotFoundException;
	public void addProduct(Product newProduct);
}
