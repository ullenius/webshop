package se.anosh.webshop.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.PersonDao;
import se.anosh.webshop.dao.exception.PersonNotFoundException;
import se.anosh.webshop.domain.Person;

@Repository
public class PersonDaoImplementation implements PersonDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Person> findAll() {
		List<Person> allPersons = em.createQuery("SELECT person FROM Person as person", Person.class).getResultList();
		return Collections.unmodifiableList(allPersons);
	}

	@Override
	public Person findById(final int id) throws PersonNotFoundException {
		try {
			TypedQuery<Person> query = em.createQuery("SELECT person FROM Person as person WHERE order.id = :id", Person.class);
			query.setParameter("id",id);
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new PersonNotFoundException("Person with id: " + id + " not found");
		}
	}

	@Override
	public void add(Person person, String username) {
		
			Query query = em.createNativeQuery("INSERT INTO persons (name, birth, city, username) VALUES (:name, :birth, :city, :username)");
			query.setParameter("name", person.getName());
			query.setParameter("birth", person.getYearOfbirth());
			query.setParameter("city", person.getCity());
			query.setParameter("username", username);
			query.executeUpdate();
	}

	@Override
	@Deprecated
	public void remove(Person person) {
		em.remove(person);
	}

	@Override
	@Deprecated
	public void update(Person updatedPerson) {
		em.merge(updatedPerson);
	}

}
