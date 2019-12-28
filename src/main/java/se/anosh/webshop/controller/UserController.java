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
import se.anosh.webshop.domain.Person;
import se.anosh.webshop.domain.User;
import se.anosh.webshop.model.AddUserModel;
import se.anosh.webshop.service.api.PersonService;
import se.anosh.webshop.service.api.UserService;
import se.anosh.webshop.util.Logger;

@Controller
@SessionScope
public class UserController {

	private final UserService userService;
	private final PersonService personService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	public UserController(UserService userService, PersonService personService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.personService = personService;
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
		Logger.log("Encrypted password: " + encryptedPassword);
		
		User user = new User(newUser.getUsername(), encryptedPassword);
		Logger.log("Success! Received: " + newUser);
		if (userService.userExists(user)) {
			newUser.setErrorMessage("Username is already in use");
			newUser.setPassword(null); // just to be on the safe side
			return new ModelAndView("create-account", "addUserModel", newUser);
		}
		
		userService.addUser(user, UserRoles.ROLE_USER);
		Person newCustomer = new Person(user.getName(), Integer.parseInt(newUser.getYearOfBirth()), newUser.getCity());
		personService.addCustomer(newCustomer,user.getName());
		
		return Redirect.success();
	}

}
