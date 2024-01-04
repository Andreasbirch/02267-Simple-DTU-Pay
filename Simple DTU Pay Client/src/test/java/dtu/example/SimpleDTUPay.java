package dtu.example;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.Transaction;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.BankCustomer;
import models.Payment;
import models.ResponseMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimpleDTUPay {

    private Client client;
    WebTarget baseUrl;
    BankService bank = new BankServiceService().getBankServicePort();

    public SimpleDTUPay() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
    }

    public ResponseMessage pay(int amount, String cid, String mid) {
        Payment payment = new Payment(amount, cid, mid);
        Response response = baseUrl.path("payment")
                .request()
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));

        return response.readEntity(ResponseMessage.class);
    }

    public List<Transaction> getPayments(String accNum) {
        return baseUrl.path("payment").path(accNum) //TODO Updated this to return all paths for a specific account.
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Transaction>>(){});
    }

    public boolean registerCustomer(BankCustomer customer) {
        return baseUrl.path("bank")
                .request()
                .post(Entity.entity(customer, MediaType.APPLICATION_JSON))
                .readEntity(Boolean.class);
    }

    public BigDecimal getAccountBalance(String accNum) {
        return baseUrl.path("bank").path(accNum) //Uses Path Parameters to access specific account
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(BigDecimal.class);
    }
}
