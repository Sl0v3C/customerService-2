package de.btc.microservice.customerservice.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESSREFERENCE")
@NamedQueries({
        @NamedQuery(name = AddressReference.ADDRESSREFERENCE_FIND_ALL, query = "SELECT ar FROM AddressReference ar"),
})
@XmlRootElement
public class AddressReference  implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ADDRESSREFERENCE_FIND_ALL = "AddressReference.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private AddressType type;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    @Column
    private Long addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
