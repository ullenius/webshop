package se.anosh.webshop.model;

import javax.validation.constraints.NotEmpty;

public class AddUserModel { // backing bean for account creation
	
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private String yearOfBirth;
	private String city;
	
	public AddUserModel() {
	}
	
	public AddUserModel(String username, String password, String yearOfBirth, String city) {
		super();
		this.username = username;
		this.password = password;
		this.yearOfBirth = yearOfBirth;
		this.city = city;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	@Override
	public String toString() {
		return "AddUserModel [username=" + username + ", password=" + password + ", yearOfBirth=" + yearOfBirth
				+ ", city=" + city + "]";
	}

}
