package se.anosh.webshop.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "persons")
@XmlRootElement
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "birth")
    private Integer yearOfbirth; // 4 digits
    private String city;
    @JoinColumn(name = "users", referencedColumnName = "username")
    @OneToOne(optional = false)
    private String username;
    
    public Person() { //default constructor required by JPA
    }
    
    public Person(String name, Integer yearOfbirth, String city, String username) {
		super();
		this.name = name;
		this.yearOfbirth = yearOfbirth;
		this.city = city;
		this.username = username;
	}

	public int getId() {
        return id;
    }

    public Integer getYearOfbirth() {
		return yearOfbirth;
	}

	public void setYearOfbirth(int yearOfbirth) {
		this.yearOfbirth = yearOfbirth;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    
	@Override
	public String toString() {
		return "Person [name=" + name + ", yearOfbirth=" + yearOfbirth + ", city=" + city + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
}
