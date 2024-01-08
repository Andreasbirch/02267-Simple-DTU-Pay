package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import models.Payment;
import models.ResponseMessage;

import java.util.List;

@Path("/payments")
public class PaymentResource {

    PaymentService service = new PaymentService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseMessage pay(Payment payment) {
        return service.pay(payment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getAll(){
        return service.getAll();
    }
}
