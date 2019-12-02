package se.anosh.webshop.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.CategoryDao;
import se.anosh.webshop.domain.Category;

@Repository
public class CategoryDaoImplementation implements CategoryDao {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Category> findAll() {
		return em.createQuery("select category from Category as category", Category.class).getResultList();
	}

	@Override
	public void add(Category category) {
		em.persist(category);
	}

	@Override
	public void remove(Category category) {
		em.remove(category);
	}

	@Override
	public void update(Category category) {
		em.merge(category);
	}
	
}
