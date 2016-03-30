package de.btc.microservice.ui;

import de.btc.microservice.core.CustomerService;
import de.btc.microservice.model.Customer;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class CustomerModel {

    @Inject
    CustomerService customerService;

    public List<Customer> getAllCustomer() {
        return customerService.loadAllCustomer();
    }

}
