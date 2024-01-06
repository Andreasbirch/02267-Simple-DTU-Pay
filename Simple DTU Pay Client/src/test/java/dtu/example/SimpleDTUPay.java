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
import models.RequestMessage;
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

    public List<Transaction> getPayments(String accNumber) {
        return (List<Transaction>) baseUrl.path("payments")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(Response.class);
    }

    public ResponseMessage pay(int amount, String cid, String mid) {
        Payment payment = new Payment(amount, cid, mid);
        Response response = baseUrl.path("payments")
                .request()
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));

        return response.readEntity(ResponseMessage.class);
    }

    public ResponseMessage registerCustomer(RequestMessage message) {
        Response response = baseUrl.path("customers")
                .request()
                .post(Entity.entity(message, MediaType.APPLICATION_JSON));

        return response.readEntity(ResponseMessage.class);
    }
}
