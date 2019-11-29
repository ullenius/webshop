package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Product;


public interface ProductService {

	public List<Product> findAllProducts();
	public Product findById(int id) throws ProductNotFoundException;
	public void addProduct(Product newProduct);
	public void removeProduct(Product product);
	public void updateProduct(Product updatedProduct);

}
