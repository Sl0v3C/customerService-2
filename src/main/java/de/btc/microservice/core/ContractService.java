package de.btc.microservice.core;

import de.btc.microservice.model.Contract;
import de.btc.microservice.model.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ContractService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Contract> loadAllContracts() {
        return em.createNamedQuery(Contract.CONTRACT_FIND_ALL, Contract.class).getResultList();
    }

    @Transactional
    public Contract findContractById(Long id) {
        return em.find(Contract.class, id);
    }


    @Transactional
    public Contract addContract(Long customerId, Contract contract) {
        Customer customer = em.find(Customer.class, customerId);
        contract.setCustomer(customer);
        em.persist(contract);
        return contract;
    }

    @Transactional
    public void deleteContract(Long id) {
        Contract Contract = em.find(Contract.class, id);
        if (Contract != null) {
            em.remove(Contract);
        }
    }
}
