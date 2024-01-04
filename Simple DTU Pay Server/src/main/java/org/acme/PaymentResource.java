package org.acme;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.Transaction;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import models.Payment;
import models.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

@Path("/payment")
public class PaymentResource {

    PaymentService service = new PaymentService();

    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    public ResponseMessage pay(Payment payment) throws BankServiceException_Exception {
        return service.pay(payment);
    }

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAll(String accNum) throws BankServiceException_Exception {
        return service.getAll(accNum);
    }
}
