package de.btc.microservice.customerservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
        @NamedQuery(name = Customer.CUSTOMER_FIND_ALL, query = "SELECT c FROM Customer c"),
        @NamedQuery(name = Customer.CUSTOMER_FIND_BY_QUERY_PARAM,
                query = "SELECT c " +
                        "  FROM Customer c " +
                        " where (:firstName is null or c.firstName = :firstName)" +
                        "   and (:name is null or c.name = :name)" +
                        "   and (:email is null or c.email = :email)")
})
@XmlRootElement
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String CUSTOMER_FIND_ALL = "Customer.findAll";
    public static final String CUSTOMER_FIND_BY_QUERY_PARAM = "Customer.findByQueryParam";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 40)
    private String name;

    @Column(length = 20)
    private String firstName;

    @Column(length = 40)
    private String email;

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
    private List<AddressReference> addressReferences;

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
    private List<Contract> contracts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressReference> getAddressReferences() {
        return addressReferences;
    }

    public void setAddressReferences(List<AddressReference> addressReferences) {
        this.addressReferences = addressReferences;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
