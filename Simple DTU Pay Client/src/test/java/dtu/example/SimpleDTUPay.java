package dtu.example;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Payment;
import models.ResponseMessage;

import java.util.ArrayList;
import java.util.Objects;

public class SimpleDTUPay {

    private Client client;
    WebTarget baseUrl;

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

    public ArrayList<Payment> getPayments() {
        return baseUrl.path("payment")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<ArrayList<Payment>>(){});
    }
}
