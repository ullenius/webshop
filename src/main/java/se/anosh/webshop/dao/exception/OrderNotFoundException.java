package se.anosh.webshop.dao.exception;

public class OrderNotFoundException extends Exception {
	
	public OrderNotFoundException() {
	}
	
	public OrderNotFoundException(String message) {
		super(message);
	}
	
	public OrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
