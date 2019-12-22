package se.anosh.webshop.service;

import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;

public interface UserService {
	
	public void addUser(User newUser, UserRoles ...roles);

}
