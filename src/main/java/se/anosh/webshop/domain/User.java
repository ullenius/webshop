package se.anosh.webshop.domain;

public class User {
	
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
