package se.anosh.webshop.dao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
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
	
	@Override
	public void add(User newUser, UserRoles... roles) {
		
		Set<UserRoles> roleSet = new HashSet<>();
		roleSet.addAll(Arrays.asList(roles));

		Query myQuery = em.createNativeQuery("INSERT INTO users ('username', 'password', 'enabled' values(:username, :password, true");
		myQuery.setParameter("username", newUser.getName());
		myQuery.setParameter("password", newUser.getPassword());
		myQuery.executeUpdate();

		for (UserRoles role: roleSet) {
			Query authoritiesQuery = em.createNativeQuery("INSERT into authorities ('username', 'authority') values(:username, :role)");
			authoritiesQuery.setParameter("username", newUser.getName());
			authoritiesQuery.setParameter("authoritiy", role.toString());
			authoritiesQuery.executeUpdate();
		}
		
		
	}

	@Override
	public User findByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
