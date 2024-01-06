package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import models.BankCustomer;
import models.RequestMessage;
import models.ResponseMessage;

import java.util.List;

@Path("/customers")
public class CustomerResource {
    CustomerService service = new CustomerService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage registerCustomer(RequestMessage requestMessage) {
        if (requestMessage.getCpr().isBlank()) {
            return new ResponseMessage(false, "No customer id supplied");
        }
        if (requestMessage.getAccNumber().isBlank()) {
            return new ResponseMessage(false, "No account number is supplied");
        }

        BankCustomer customer = new BankCustomer(requestMessage.getCpr(), requestMessage.getAccNumber());
        return service.registerCustomer(customer);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankCustomer> getCustomers() {
        return service.getCustomers();
    }

    @GET
    @Path("/{customerId}") //Maps customerId to the :accountNumber path parameter
    @Produces(MediaType.APPLICATION_JSON)
    public BankCustomer getCustomer(@PathParam("customerId") String customerId) {
        return service.getCustomer(customerId);
    }
}
