package de.btc.microservice.customerservice.core;

import de.btc.microservice.customerservice.model.AddressReference;
import de.btc.microservice.customerservice.model.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AddressReferenceService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<AddressReference> loadAllAddressReferences() {
        return em.createNamedQuery(AddressReference.ADDRESSREFERENCE_FIND_ALL, AddressReference.class).getResultList();
    }

    @Transactional
    public AddressReference findAddressReferenceById(Long id) {
        return em.find(AddressReference.class, id);
    }


    @Transactional
    public AddressReference addAddressReference(Long customerId, AddressReference addressReference) {
        Customer customer = em.find(Customer.class, customerId);
        addressReference.setCustomer(customer);
        em.persist(addressReference);
        return addressReference;
    }

    @Transactional
    public void deleteAddressReference(Long id) {
        AddressReference addressReference = em.find(AddressReference.class, id);
        if (addressReference != null) {
            em.remove(addressReference);
        }
    }
}
