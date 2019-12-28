package se.anosh.webshop.domain;

import java.io.Serializable;


public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String name;
	private final String password;
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	public String getName() {
		return name.toLowerCase();
	}
	public String getPassword() {
		return password;
	}
	
	public String toString() {
		return "Username: " + getName();
	}

}
