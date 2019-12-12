package se.anosh.webshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.ProductDao;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Product;
@Repository
public class ProductDaoImplementation implements ProductDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Product> findAll() {
		return em.createQuery("select product from Product as product", Product.class).getResultList();
	}

	@Override
	public Product findById(final int id) throws ProductNotFoundException {
		try {
			TypedQuery<Product> query = em.createQuery("SELECT product FROM Product as product WHERE product.id = :id", Product.class);
			query.setParameter("id",id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new ProductNotFoundException("Product with id: " + id + " not found");
		}
		
	}

	@Override
	public void add(Product item) {
		em.persist(item);
	}

	@Override
	public void remove(Product item) {
		em.remove(item);
	}

	@Override
	public void update(Product item) {
		em.merge(item);
	}
	
	
	
	
	

}
