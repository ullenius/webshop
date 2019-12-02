package se.anosh.webshop.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.ProductDao;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

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
	
	@Override
	public List<Product> findByName(String match) {
		if (match == null || match.isEmpty())
			return Collections.emptyList();
			
		return findMatchingOrders( (product) -> product.getName().contains(match));
	}
	
	private List<Product> findMatchingOrders(Predicate<Product> criteria) {
		List<Product> allProducts = findAllProducts();
		List<Product> matching = new LinkedList<>();
		for (Product product : allProducts) {
			if (criteria.test(product))
				matching.add(product);
		}
		return Collections.unmodifiableList(matching);
	}
	

}
