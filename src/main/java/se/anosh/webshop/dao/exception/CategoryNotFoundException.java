package se.anosh.webshop.dao.exception;

public class CategoryNotFoundException extends Exception {
	
	public CategoryNotFoundException() {
	}
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	
	public CategoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}


}
