package se.anosh.webshop.dao;

import se.anosh.webshop.dao.api.UserDao;
import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;

public class UserDaoImplementation implements UserDao {

	@Override
	public void add(User newUser, UserRoles... roles) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
