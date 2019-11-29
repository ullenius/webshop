package se.anosh.webshop.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.ProductDao;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Product;

@Service
@Transactional
public class ProductImpl implements ProductService {

	@Autowired
	private ProductDao dao;
	
	@Override
	public List<Product> findAllProducts() {
		return dao.findAll();
	}

	@Override
	public Product findById(int id) throws ProductNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void addProduct(Product newProduct) {
		dao.add(newProduct);
	}

	@Override
	public void removeProduct(Product product) {
		dao.remove(product);
	}

	@Override
	public void updateProduct(Product updatedProduct) {
		dao.update(updatedProduct);
	}

}
