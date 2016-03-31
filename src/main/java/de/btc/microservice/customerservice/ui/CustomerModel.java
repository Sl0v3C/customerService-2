package de.btc.microservice.customerservice.ui;

import de.btc.microservice.customerservice.core.CustomerService;
import de.btc.microservice.customerservice.model.Customer;

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
