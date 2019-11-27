package se.anosh.webshop.service;

import java.util.List;

import se.anosh.webshop.dao.PersonNotFoundException;
import se.anosh.webshop.domain.Person;

public interface PersonService {

	public List<Person> findAll();
	public Person findById(int id) throws PersonNotFoundException;
	public void addCustomer(Person newPerson);
	public void removeCustomer(Person person);
	public void updateCustomer(Person peron);
}
