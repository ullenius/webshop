package se.anosh.webshop.dao.api;

import java.util.List;

import se.anosh.webshop.dao.ProductNotFoundException;
import se.anosh.webshop.domain.Product;

public interface ProductDao {
	
	public List<Product> findAll();
	public Product findById(int id) throws ProductNotFoundException;
	public void add(Product item);
	public void remove(Product item);
	public void update(Product item);

}
