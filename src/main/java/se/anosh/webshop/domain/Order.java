package se.anosh.webshop.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
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
import javax.persistence.Transient;
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
    @Transient
    @Deprecated // add to database-table or remove
    private final long creationTimeStampNanoSeconds;

    public Order() {
    	creationTimeStampNanoSeconds = System.nanoTime();
    }

    public Order(Date datum) {
        this.date = datum;
        creationTimeStampNanoSeconds = System.nanoTime();
    }
    
    public long getNanoStamp() {
    	return creationTimeStampNanoSeconds;
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", customer=" + customer + "]";
	}
	
	
	
    
}
