package se.anosh.webshop.dao;

import java.util.List;

import se.anosh.webshop.domain.Person;


public interface PersonDao {
	
	public List<Person> findAll();
	public Person findById(int id) throws PersonNotFoundException;
	public void add(Person item);
	public void remove(Person item);
	public void update(Person item);


}
