package se.anosh.webshop.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.anosh.webshop.dao.api.UserDao;
import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao dao;

	@Override
	public void addUser(User newUser, UserRoles... roles) {
		dao.add(newUser, roles);
	}
	
	@Override
	public boolean userExists(User user) {
		return dao.contains(user);
	}

	@Override
	public int findUserId(String username) {
		return dao.findId(username);
	}

}
