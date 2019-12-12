package se.anosh.webshop.controller;

import org.springframework.web.servlet.ModelAndView;

final class Redirect {
	
	private Redirect() {
		throw new AssertionError("Cannot be instantiated");
	}
	
	static ModelAndView success() {
		return new ModelAndView("redirect:/success.html");
	}
	
	static ModelAndView error() {
		return new ModelAndView("redirect:/error.html");
	}

}
