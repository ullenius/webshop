package se.anosh.webshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.dao.api.UserRoles;
import se.anosh.webshop.domain.User;
import se.anosh.webshop.model.AddUserModel;
import se.anosh.webshop.service.PersonService;
import se.anosh.webshop.service.UserService;

@Controller
@SessionScope
public class UserController {

	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserController(UserService userService, PersonService customerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@GetMapping(value="/login")
	public ModelAndView loginPage() {
		return new ModelAndView("redirect:/login.html");
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
		
		// check if username is already in use
		final String encryptedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
		System.out.println("Encrypted password: " + encryptedPassword);
		
		User user = new User(newUser.getUsername(), encryptedPassword);
		System.out.println("Success! Received: " + newUser); // debug
		if (userService.userExists(user)) {
			newUser.setErrorMessage("Username is already in use");
			newUser.setPassword(null); // just to be on the safe side
			return new ModelAndView("create-account", "addUserModel", newUser);
		}
		
		userService.addUser(user, UserRoles.ROLE_USER);
		
		
		return Redirect.success();
	}

}
