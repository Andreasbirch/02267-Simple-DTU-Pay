package org.acme;

import dtu.ws.fastmoney.BankServiceException_Exception;
import jakarta.ws.rs.*;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import models.BankCustomer;

import java.math.BigDecimal;

@Path("/bank")
public class BankResource {

    BankService service = new BankService();

    //TODO This doesn't work with postman for some reason. The customer will be added with a null id
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public boolean registerCustomer(BankCustomer customer) {
//        return service.registerCustomer(customer);
//    }

    //TODO Right now, this saves the whole json object into the id property
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean registerCustomer(String id, String accNumber) {
        return service.registerCustomer(new BankCustomer(id, accNumber));
    }

    @GET
    @Path("/{accountNumber}") //Maps customerId to the :accountNumber path parameter
    @Produces(MediaType.APPLICATION_JSON)
    public BankCustomer getCustomer(@PathParam("accountNumber") String customerId) {
        return service.getCustomer(customerId);
    }

    //TODO This has same endpoint as the above, but should properly return an account balance
//    @GET
//    @Path("/{accountNumber}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public BigDecimal getAccountBalance(@PathParam("accountNumber") String accNum) throws BankServiceException_Exception {
//        return service.getAccountBalance(accNum);
//    }
}
