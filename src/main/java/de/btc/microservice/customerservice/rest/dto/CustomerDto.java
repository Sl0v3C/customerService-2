package de.btc.microservice.customerservice.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String firstName;
    private String email;
    private List<Long> addressReferenceIds;
    private List<Long> contractIds;

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

    public List<Long> getAddressReferenceIds() {
        return addressReferenceIds;
    }

    public void setAddressReferenceIds(List<Long> addressReferenceIds) {
        this.addressReferenceIds = addressReferenceIds;
    }

    public List<Long> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Long> contractIds) {
        this.contractIds = contractIds;
    }
}
