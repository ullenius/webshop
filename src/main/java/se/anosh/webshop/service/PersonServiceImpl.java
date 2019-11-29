package se.anosh.webshop.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.PersonDao;
import se.anosh.webshop.dao.exception.PersonNotFoundException;
import se.anosh.webshop.domain.Person;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDao dao;

	@Override
	public List<Person> findAll() {
		return dao.findAll();
	}

	@Override
	public Person findById(int id) throws PersonNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void addCustomer(Person person) {
		dao.add(person);
	}

	@Override
	public void removeCustomer(Person person) {
		dao.remove(person);
	}

	@Override
	public void updateCustomer(Person updatedCustomer) {
		dao.update(updatedCustomer);
	}
	

}
