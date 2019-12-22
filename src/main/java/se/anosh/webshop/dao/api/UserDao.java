package se.anosh.webshop.dao.api;

import se.anosh.webshop.domain.User;

public interface UserDao {
	
	public void add(User newUser, UserRoles ...roles );
	public User findByName(String username);

}
