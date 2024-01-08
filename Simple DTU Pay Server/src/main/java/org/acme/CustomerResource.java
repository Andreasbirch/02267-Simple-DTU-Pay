package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import models.BankCustomer;
import models.RequestMessage;
import models.ResponseMessage;

import java.util.List;

@Path("/customers")
public class CustomerResource {
    static CustomerService service = new CustomerService();

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
    public List<String> getCustomers() {
        return service.getCustomers();
    }

    @DELETE
    @Path("/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void retireCustomer(@PathParam("customerId") String customerId) {
        service.removeCustomer(customerId);
    }
}
