package se.anosh.webshop.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.CategoryDao;
import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao dao;

	@Override
	public List<Category> findAll() {
		List<Category> categories = dao.findAll();
		categories.sort(null);
		return Collections.unmodifiableList(categories);
	}

	@Override
	public void addCategory(Category newCategory) {
		dao.add(newCategory);
	}

	@Override
	public void removeCategory(Category category) {
		dao.remove(category);
	}

	@Override
	public void updateCategory(Category category) {
		dao.update(category);
	}

	@Override
	public Category findById(int id) throws CategoryNotFoundException {
		return dao.findById(id);
	}

}
