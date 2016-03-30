package de.btc.microservice.rest;

import de.btc.microservice.core.CustomerService;
import de.btc.microservice.model.Customer;
import de.btc.microservice.rest.dto.CustomerDto;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customer")
public class CustomerRestService {

    @Inject
    CustomerService customerService;

    @GET
    @Path("/")
    @Produces("application/json")
    public List<CustomerDto> getAllCustomers() {
        return customerService.loadAllCustomer().stream().map(customer -> mapCustomerToDto(customer)).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public CustomerDto getCustomerById(@PathParam("id") Long id) {
        return mapCustomerToDto(customerService.findCustomerById(id));
    }


    @GET
    @Path("/query")
    @Produces("application/json")
    public List<CustomerDto> getCustomerById(@QueryParam("name") String name,
                                             @QueryParam("firstName") String firstName,
                                             @QueryParam("email") String email) {
        return customerService.findCustomerByQueryParam(firstName, name, email).stream().map(customer -> mapCustomerToDto(customer)).collect(Collectors.toList());
    }


    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public CustomerDto updateCustomer(@PathParam("id") Long id,
                                      @QueryParam("name") String name,
                                      @QueryParam("firstName") String firstName,
                                      @QueryParam("email") String email) {
        return mapCustomerToDto(customerService.updateCustomer(id, name, firstName, email));
    }


    @POST
    @Path("/")
    @Produces("application/json")
    public CustomerDto addCustomer(CustomerDto customer) {
        return mapCustomerToDto(customerService.addCustomer(mapDtoToCustomer(customer)));
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void deleteCustomer(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
    }


    private CustomerDto mapCustomerToDto(de.btc.microservice.model.Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        if (customer.getAddressReferences() != null) {
            customerDto.setAddressReferenceIds(customer.getAddressReferences().stream().map(addressReference -> addressReference.getId()).collect(Collectors.toList()));
        }
        if (customer.getContracts() != null) {
            customerDto.setContractIds(customer.getContracts().stream().map(contract -> contract.getId()).collect(Collectors.toList()));
        }
        return customerDto;
    }

    private Customer mapDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }
}
