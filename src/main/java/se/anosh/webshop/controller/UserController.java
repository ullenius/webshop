package se.anosh.webshop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import se.anosh.webshop.model.AddUserModel;

@Controller
@SessionScope
public class UserController {

	@Autowired
	public UserController() {
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
		
//		List<GrantedAuthority> roles = new ArrayList<>();
//		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//		User user = new User(newUser.getUsername(), newUser.getPassword(), roles);
		
		
		System.out.println("Success! Received: " + newUser);
		
		return Redirect.success();
	}

}
