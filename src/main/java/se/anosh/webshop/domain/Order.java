package se.anosh.webshop.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "orders")
@XmlRootElement
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "customer", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person customer;

    public Order() {
    }

    public Order(Date datum) {
        this.date = datum;
    }

    public int getId() {
        return id;
    }

    public Date getDatum() {
        return date;
    }
    
    public void setDate(Date date) {
    	this.date = date;
    }

    public Person getCustomer() {
        return customer;
    }

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", customer=" + customer + "]";
	}
    
}
