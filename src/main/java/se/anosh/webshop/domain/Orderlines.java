package se.anosh.webshop.domain;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "orderlines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderlines.findAll", query = "SELECT o FROM Orderlines o")
    , @NamedQuery(name = "Orderlines.findById", query = "SELECT o FROM Orderlines o WHERE o.id = :id")
    , @NamedQuery(name = "Orderlines.findByQuantity", query = "SELECT o FROM Orderlines o WHERE o.quantity = :quantity")})
public class Orderlines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "order", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders order1;
    @JoinColumn(name = "product", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Products product;

    public Orderlines() {
    }

    public Orderlines(Integer id) {
        this.id = id;
    }

    public Orderlines(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder1() {
        return order1;
    }

    public void setOrder1(Orders order1) {
        this.order1 = order1;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderlines)) {
            return false;
        }
        Orderlines other = (Orderlines) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "webshop.Orderlines[ id=" + id + " ]";
    }
    
}
