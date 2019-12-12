package se.anosh.webshop.rest;

import org.springframework.web.servlet.ModelAndView;

class Redirect {
	
	static ModelAndView success() {
		return new ModelAndView("redirect:/success.html");
	}

}
