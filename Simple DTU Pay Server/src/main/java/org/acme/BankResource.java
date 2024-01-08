package org.acme;

import dtu.ws.fastmoney.BankServiceException_Exception;
import jakarta.ws.rs.*;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import models.BankCustomer;

import javax.print.attribute.standard.Media;
import java.math.BigDecimal;
import java.util.List;

@Path("/bank")
public class BankResource {

    BankService service = new BankService();



    @GET
    @Path("/{accountNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public BigDecimal getAccountBalance(@PathParam("accountNumber") String accNum) throws BankServiceException_Exception {
        return BigDecimal.valueOf(0);
    }
}
