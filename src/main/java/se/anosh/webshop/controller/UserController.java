package se.anosh.webshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.UserDaoImplementation;
import se.anosh.webshop.dao.api.UserDao;
import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;
import se.anosh.webshop.model.AddUserModel;
import se.anosh.webshop.service.UserService;

@Controller
@SessionScope
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(value="/addUser")
	public ModelAndView newUser() {
		return new ModelAndView("create-account", "addUserModel", new AddUserModel());
	}
	
	@PostMapping(value="/saveUser")
	public ModelAndView saveUser(@Valid AddUserModel newUser, Errors results) {
		
		if (results.hasErrors()) {
			return new ModelAndView("create-account", "addUserModel", newUser);
		}
		
		System.out.println("Success! Received: " + newUser);
		
		User user = new User(newUser.getUsername(), newUser.getPassword());
		userService.addUser(user, UserRoles.ROLE_USER);
		
		return Redirect.success();
	}

}
