package de.btc.microservice.customerservice.rest;


import de.btc.microservice.customerservice.core.AddressReferenceService;
import de.btc.microservice.customerservice.model.AddressReference;
import de.btc.microservice.customerservice.rest.dto.AddressReferenceDto;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

@Path("/addressReference")
public class AddressReferenceRestService {

    @Inject
    AddressReferenceService addressReferenceService;

    @GET
    @Path("/")
    @Produces("application/json")
    public List<AddressReferenceDto> getAllAddressReferences() {
        List<AddressReference> addressReferences = addressReferenceService.loadAllAddressReferences();
        return addressReferences.stream().map(addressReference -> mapAddressReferenceToDto(addressReference)).collect(Collectors.toList());
    }

    private AddressReferenceDto mapAddressReferenceToDto(AddressReference addressReference) {
        AddressReferenceDto addressReferenceDto = new AddressReferenceDto();
        addressReferenceDto.setId(addressReference.getId());
        addressReferenceDto.setAddressId(addressReference.getAddressId());
        addressReferenceDto.setType(addressReference.getType());
        addressReferenceDto.setCustomerId(addressReference.getCustomer().getId());
        return addressReferenceDto;
    }

    private AddressReference mapDtoToAddressReference(AddressReferenceDto addressReferenceDto) {
        AddressReference addressReference = new AddressReference();
        addressReference.setId(addressReferenceDto.getId());
        addressReference.setAddressId(addressReferenceDto.getAddressId());
        addressReference.setType(addressReferenceDto.getType());
        return addressReference;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public AddressReferenceDto getAddressReferenceById(@PathParam("id") Long id) {
        AddressReference addressReference = addressReferenceService.findAddressReferenceById(id);
        if (addressReference != null) {
            return mapAddressReferenceToDto(addressReference);
        }
        return null;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public AddressReferenceDto addAddressReference(@QueryParam("customerId") Long customerId,
                                   AddressReferenceDto addressReferenceDto) {
        if (customerId != null) {
            return mapAddressReferenceToDto(addressReferenceService.addAddressReference(customerId, mapDtoToAddressReference(addressReferenceDto)));
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void deleteAddressReference(@PathParam("id") Long id) {
        addressReferenceService.deleteAddressReference(id);
    }
}
