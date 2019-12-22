package se.anosh.webshop.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import se.anosh.webshop.dao.api.UserDao;
import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;

/*
 * Manages the users and authorities tables in the database
 */
@Repository
public class UserDaoImplementation implements UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
	private static final String INSERT_USER = "INSERT INTO users (username, password, enabled) values(:username, :password, true)";
	private static final String ADD_USER_ROLE = "INSERT into authorities (username, authority) values(:username, :role)";
	private static final String SELECT_USER = "SELECT (username) FROM users WHERE username = :username";
	
	@Override
	public void add(User newUser, UserRoles ...roles) {
		
		Set<UserRoles> roleSet = new HashSet<>();
		roleSet.addAll(Arrays.asList(roles));

		Query myQuery = em.createNativeQuery(INSERT_USER);
		myQuery.setParameter("username", newUser.getName());
		myQuery.setParameter("password", newUser.getPassword());
		myQuery.executeUpdate();

		for (UserRoles role: roleSet) {
			Query authoritiesQuery = em.createNativeQuery(ADD_USER_ROLE);
			authoritiesQuery.setParameter("username", newUser.getName());
			authoritiesQuery.setParameter("role", role.toString());
			authoritiesQuery.executeUpdate();
		}
		
	}

	@Override
	public boolean contains(String username) {
		
		Query myQuery = em.createNativeQuery(SELECT_USER);
		myQuery.setParameter("username", username);
		try {
			myQuery.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		return true;
	}

}
