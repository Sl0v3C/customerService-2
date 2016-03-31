package de.btc.microservice.customerservice.core;

import de.btc.microservice.customerservice.model.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CustomerService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Customer> loadAllCustomer() {
        return em.createNamedQuery(Customer.CUSTOMER_FIND_ALL, Customer.class).getResultList();
    }

    @Transactional
    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }


    @Transactional
    public List<Customer> findCustomerByQueryParam(String firstName, String name, String email) {
        return em.createNamedQuery(Customer.CUSTOMER_FIND_BY_QUERY_PARAM, Customer.class)
                .setParameter("firstName", firstName)
                .setParameter("name", name)
                .setParameter("email", email)
                .getResultList();
    }

    @Transactional
    public Customer updateCustomer(Long id, String name, String firstName, String email) {
        Customer persistedCustomer = em.find(Customer.class, id);
        if (name != null) {
            persistedCustomer.setName(name);
        }
        if (firstName != null) {
            persistedCustomer.setFirstName(firstName);
        }
        if (email != null) {
            persistedCustomer.setEmail(email);
        }
        return persistedCustomer;
    }

    @Transactional
    public Customer addCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
        }
    }
}
