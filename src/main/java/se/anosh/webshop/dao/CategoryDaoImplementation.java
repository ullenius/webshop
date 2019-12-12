package se.anosh.webshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.CategoryDao;
import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.dao.exception.ProductNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.domain.Product;

@Repository
public class CategoryDaoImplementation implements CategoryDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Category> findAll() {
		return em.createQuery("select category from Category as category", Category.class).getResultList();
	}

	@Override
	public Category findById(int id) throws CategoryNotFoundException {
		
		try {
			TypedQuery<Category> query = em.createQuery("SELECT category FROM Category as category WHERE category.id = :id", Category.class);
			query.setParameter("id",id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new CategoryNotFoundException("Category with id: " + id + " not found");
		}
		
	}
	
}
