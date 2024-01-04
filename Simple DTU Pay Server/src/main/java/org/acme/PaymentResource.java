package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/payment")
public class PaymentResource {

    PaymentService service = new PaymentService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean pay(int amount, String cid, String mid) {
        return service.pay(amount, cid, mid);
    }
}
