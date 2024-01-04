package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import models.Payment;

import java.util.ArrayList;

@Path("/payment")
public class PaymentResource {

    PaymentService service = new PaymentService();

    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    public boolean pay(Payment payment) {
        return service.pay(payment);
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Payment> getAll() {
        return service.getAll();
    }
}
