package se.anosh.webshop.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.CategoryDao;
import se.anosh.webshop.dao.exception.CategoryNotFoundException;
import se.anosh.webshop.domain.Category;
import se.anosh.webshop.service.api.CategoryService;

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
	public Category findById(int id) throws CategoryNotFoundException {
		return dao.findById(id);
	}

}
