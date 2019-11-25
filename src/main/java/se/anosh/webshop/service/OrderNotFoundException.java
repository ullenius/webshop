package se.anosh.webshop.service;

public class OrderNotFoundException extends Exception implements CustomException {
	
	public OrderNotFoundException() {
	}
	
	public OrderNotFoundException(String message) {
		super(message);
	}
	
	public OrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
