package de.btc.microservice.customerservice.rest;


import de.btc.microservice.customerservice.core.ContractService;
import de.btc.microservice.customerservice.model.Contract;
import de.btc.microservice.customerservice.rest.dto.ContractDto;

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

@Path("/contract")
public class ContractRestService {

    @Inject
    ContractService contractService;

    @GET
    @Path("/")
    @Produces("application/json")
    public List<ContractDto> getAllContracts() {
        List<Contract> contracts = contractService.loadAllContracts();
        return contracts.stream().map(contract -> mapContractToDto(contract)).collect(Collectors.toList());
    }

    private ContractDto mapContractToDto(Contract contract) {
        ContractDto contractDto = new ContractDto();
        contractDto.setId(contract.getId());
        contractDto.setContractNumber(contract.getContractNumber());
        contractDto.setCustomerId(contract.getCustomer().getId());
        return contractDto;
    }

    private Contract mapDtoToContract(ContractDto contractDto) {
        Contract contract = new Contract();
        contract.setId(contractDto.getId());
        contract.setContractNumber(contractDto.getContractNumber());
        return contract;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public ContractDto getContractById(@PathParam("id") Long id) {
        Contract contract = contractService.findContractById(id);
        if (contract != null) {
            return mapContractToDto(contract);
        }
        return null;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    public ContractDto addContract(@QueryParam("customerId") Long customerId,
                                   ContractDto contractDto) {
        if (customerId != null) {
            return mapContractToDto(contractService.addContract(customerId, mapDtoToContract(contractDto)));
        }
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public void deleteContract(@PathParam("id") Long id) {
        contractService.deleteContract(id);
    }
}
