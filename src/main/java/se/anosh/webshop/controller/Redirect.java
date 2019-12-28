package se.anosh.webshop.controller;

import org.springframework.web.servlet.ModelAndView;

final class Redirect {
	
	private static final String SUCCESS_PAGE = "success.html";
	private static final String ERROR_PAGE = "error.html";
	
	private Redirect() {
		throw new AssertionError("Cannot be instantiated");
	}
	
	static ModelAndView success() {
		return new ModelAndView("redirect:/" + SUCCESS_PAGE);
	}
	
	static ModelAndView error() {
		return new ModelAndView("redirect:/" + ERROR_PAGE);
	}

}
