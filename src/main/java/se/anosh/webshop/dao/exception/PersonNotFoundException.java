package se.anosh.webshop.dao.exception;

public class PersonNotFoundException extends Exception {
	
	public PersonNotFoundException() {
	}
	
	public PersonNotFoundException(String message) {
		super(message);
	}
	
	public PersonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}


}
