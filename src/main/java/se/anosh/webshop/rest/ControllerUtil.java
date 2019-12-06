package se.anosh.webshop.rest;

import org.springframework.web.servlet.ModelAndView;

public final class ControllerUtil {
	
	private ControllerUtil() {
		throw new AssertionError("Class cannot be instantiated");
	}

	public static ModelAndView errorPage() {
		return new ModelAndView("error");
	}

}
